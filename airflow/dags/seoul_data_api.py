from airflow import DAG
from datetime import datetime, timedelta
from airflow.operators.bash import BashOperator
from airflow.operators.empty import EmptyOperator
from airflow.operators.python import PythonOperator, BranchPythonOperator
from airflow.models.variable import Variable
import pendulum
import pandas as pd
import numpy as np

from urllib import parse
from urllib.request import urlopen
from bs4 import BeautifulSoup as bs


import csv
import logging
from airflow.operators.python import PythonOperator
from airflow.providers.mysql.hooks.mysql import MySqlHook
from sqlalchemy import create_engine
import pymysql


API_KEY = Variable.get("SEOUL_TRANS_API")
SEOUL_DATA_PATH = Variable.get("SEOUL_DATA_PATH")
local_tz = pendulum.timezone("Asia/Seoul")

default_args = {
    'owner': "soobeen",
    'depends_on_past': False,
    'start_date': datetime(2023, 11, 10, tzinfo=local_tz),
    'retries': 0,
}

seoul_dag = DAG(
    'seoul_trans_api',
    default_args=default_args,
    schedule="0 9 * * *",
    user_defined_macros={'local_dt': lambda execution_date: execution_date.in_timezone(local_tz).strftime("%Y-%m-%d %H:%M:%S")},
)

# 현재 db 테이블 가져오기 
def get_current_tb():
    # hook 내용 작성
    logging.info("Started mysql_hook")
    hook = MySqlHook.get_hook(conn_id="seoul-mysql") # 미리 정의한 mysql connection 적용
    conn = hook.get_conn() # connection 하기
    cursor = conn.cursor() # cursor객체 만들기 
    cursor.execute("use seoul") # sql문 수행
    cursor.execute("select * from trans")
    # csv파일 만들기
    with open(f"{SEOUL_DATA_PATH}/get_data_mysql.csv", "w") as f:
        csv_writer = csv.writer(f)
        csv_writer.writerow([i[0] for i in cursor.description])
        csv_writer.writerows(cursor)
        f.flush()
        cursor.close()
        conn.close()
        logging.info("Saved data : %s", "get_data_mysql.csv")


# api로부터 데이터 받아오기
def get_api(**context):
    url = f'http://openapi.seoul.go.kr:8088/{API_KEY}/xml/tbLnOpendataRtmsV/1/100/'

    result = urlopen(url) 
    data = bs(result, 'lxml-xml') 
    rows=data.find_all('row')

    row_list = [] 
    name_list = [] 
    value_list = [] 

    # 데이터 수집
    for i in range(0, len(rows)):
        columns = rows[i].find_all()
        for j in range(0,len(columns)):
            if i ==0:
                name_list.append(columns[j].name)
            value_list.append(columns[j].text)
        row_list.append(value_list)
        value_list=[]

    transfer_data = {'row':row_list,'name':name_list}

    return transfer_data

# 데이터 추출 및 중복 체크 
def extract_data(**context):
    transfer_data=context['task_instance'].xcom_pull(task_ids='Get.api_data')

    row_list = transfer_data['row']
    name_list = transfer_data['name']

    raw=pd.DataFrame(row_list, columns=name_list)[['ACC_YEAR','SGG_CD','SGG_NM','BJDONG_CD','BJDONG_NM','LAND_GBN',
                                            'LAND_GBN_NM','BONBEON','BUBEON','BLDG_NM','DEAL_YMD','FLOOR','BUILD_YEAR',
                                            'OBJ_AMT','HOUSE_TYPE','BLDG_AREA']]

    raw.rename(columns={'ACC_YEAR':'접수연도','SGG_CD':'자치구코드','SGG_NM':'자치구명','BJDONG_CD':'행정동코드','BJDONG_NM':'행정동명',
                        'LAND_GBN':'지번구분','LAND_GBN_NM':'지번구분명','BONBEON':'본번','BUBEON':'부번','BLDG_NM':'건물명','DEAL_YMD':'계약일',
                        'FLOOR':'층','BUILD_YEAR':'건축년도','OBJ_AMT':'거래금액','HOUSE_TYPE':'건물용도','BLDG_AREA':'건물면적(㎡)'},inplace=True)

    raw=raw.astype({'건물면적(㎡)':'float'})

    idx = raw[raw['건물명']=='힐스테이트 서초 젠트리스'].index

    for i in idx:
        raw.loc[i,'본번']=557
        raw.loc[i,'부번']=0

    idx = raw[raw['건물명']=='자연누리 오피스텔'].index

    for i in idx:
        raw.loc[i,'본번']=662
        raw.loc[i,'부번']=0

    address=[]

    for i in range(0,len(raw)):
        if raw.loc[i,'본번'] == '':
            tmp=f"서울특별시 {raw.loc[i,'자치구명']} {raw.loc[i,'행정동명']}"

        elif raw.loc[i,'지번구분명']=='산':
            tmp=f"서울특별시 {raw.loc[i,'자치구명']} {raw.loc[i,'행정동명']} 산{int(raw.loc[i,'본번'])}-{int(raw.loc[i,'부번'])}"
            tmp=tmp.replace('-0','')

        else:
            tmp=f"서울특별시 {raw.loc[i,'자치구명']} {raw.loc[i,'행정동명']} {int(raw.loc[i,'본번'])}-{int(raw.loc[i,'부번'])}"
            tmp=tmp.replace('-0','')

        address.append(tmp)

    raw.drop(columns={'지번구분','지번구분명','본번','부번'},inplace=True)
    raw['주소']=address
    raw['평수']=round(raw['건물면적(㎡)']*0.3025,1)

    raw.drop(columns={'건물면적(㎡)'},inplace=True)

    # 기존 테이블과 비교하여 새로운 데이터 추출
    current_tb=pd.read_csv(f"{SEOUL_DATA_PATH}/get_data_mysql.csv")
    current_tb.drop(columns={'id','지역코드'},inplace=True)
    current_tb=current_tb.astype({'접수연도':'str','자치구코드':'str','행정동코드':'str','계약일':'str','거래금액':'str'})
    current_tb.fillna('',inplace=True)
    current_tb['계약일']=list(map(lambda x : x.replace("-",""),current_tb['계약일']))

    tmp=pd.merge(raw.drop_duplicates(),current_tb,how='outer',indicator=True)
    new_data=tmp.query('_merge=="left_only"')
    new_data['지역코드']=new_data['자치구코드']+new_data['행정동코드']
    cnt=len(new_data)
    logging.info('%d건 업데이트 확인',cnt)

    new_data.drop(columns={'_merge'}).to_csv(f'{SEOUL_DATA_PATH}/new_data.csv',index=False)


def check():
    new_data=pd.read_csv(f'{SEOUL_DATA_PATH}/new_data.csv')
    cnt = len(new_data)

    if cnt != 0 :
        return 'Insert.new_data'
    else:
        return 'Skip.insert'


def insert_sql():
    new_data=pd.read_csv(f'{SEOUL_DATA_PATH}/new_data.csv')
    print(new_data)

    conn = MySqlHook.get_connection(conn_id="seoul-mysql") # 미리 정의한 mysql connection 적용 
    username = conn.login
    pw = conn.password
    db_ip = conn.host
    db_port = conn.port
    db_name = conn.schema

    db_connection = f"mysql+pymysql://{username}:{pw}@{db_ip}:{db_port}/{db_name}"
    engine = create_engine(db_connection)

    new_data.to_sql(con=engine, name='trans',if_exists='append',index=False)

    

get_current_tb = PythonOperator(task_id='Get.current_data',
                                python_callable=get_current_tb,
                                dag=seoul_dag)

get_api_data =  PythonOperator(task_id='Get.api_data',
                                python_callable=get_api,
                                dag=seoul_dag)

extract_data = PythonOperator(task_id='Extract.new_data',
                                python_callable=extract_data,
                                dag=seoul_dag)

branching = BranchPythonOperator(task_id='Check.new_data',
                                 python_callable=check,
                                 dag=seoul_dag)

insert_data = PythonOperator(task_id='Insert.new_data',
                                python_callable=insert_sql,
                                dag=seoul_dag)

skip_insert = EmptyOperator(task_id='Skip.insert',
                                dag=seoul_dag)

finish = EmptyOperator(
    task_id='finish',
    trigger_rule='one_success',
    dag=seoul_dag,
)


[get_current_tb, get_api_data] >> extract_data >> branching 
branching >> insert_data >> finish
branching  >> skip_insert >> finish
