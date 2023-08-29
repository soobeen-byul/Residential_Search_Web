from urllib.request import urlopen, Request
import os, configparser, datetime
import mysql.connector
import xml.etree.ElementTree as ET
from bs4 import BeautifulSoup as bs

def get_mt20id(ST_DT): # end_date는 Dag에서 start_date(execution_date가 되겠지요?)기준 timedelta로 +4주로 계산

    SERVICE_KEY='1622ef3e02314464a8bfba2ea8345e15'
    CPAGE=1
    ROWS= '10000'   # 가져오는 행수 : 실제 deploy 시에는 100000개 가져오기
    db_insert_cnt = 0

    ST_DT = datetime.datetime.strptime(ST_DT, '%Y-%m-%d')
    END_DT= (ST_DT + datetime.timedelta(weeks=4)).strftime("%Y%m%d")
    ST_DT= ST_DT.strftime("%Y%m%d")
    print(ST_DT,END_DT)

    url = f'http://www.kopis.or.kr/openApi/restful/pblprfr?service={SERVICE_KEY}&stdate={ST_DT}&eddate={END_DT}&cpage={CPAGE}&rows={ROWS}'

    result = urlopen(url)
    data = bs(result, 'lxml-xml')
    db = data.find_all('db')
    # print(len(db))
    
    id=[]
    nm=[]
    author=[]
    creator=[]
    
    for pf in db :
        pf_id = pf.find('mt20id').text
        pf_nm = pf.find('prfnm').text

        try:
            pf_author = pf.find('author').text
        except:
            pf_author = pf.find('author')

        try:
            pf_creator = pf.find('creator').text
        except:
            pf_creator = pf.find('creator')

        id.append(pf_id)
        nm.append(pf_nm)
        author.append(pf_author)
        creator.append(pf_creator)


    data_dict={}
    data_dict['mt20id']=id
    data_dict['name']=nm
    data_dict['author']=author
    data_dict['creator']=creator
    
    print(len(data_dict['name']))

    print(data_dict['name'])
    # for idx,id in enumerate(data_dict['mt20id']):
    #     check_query = f"SELECT * FROM performance WHERE pf_id = %s"
    #     cur.execute(check_query,(id,))
    #     result = cur.fetchall()
        
    #     if result != []:
    #         print("중복값 존재. bye")

    #     else:
    #         name = data_dict['name'][idx]
    #         author = data_dict['author'][idx]
    #         creator = data_dict['creator'][idx]

    #         ex_query = "INSERT INTO performance(pf_id, pf_nm, author, creator) VALUES (%s,%s,%s,%s)"
    #         db_insert_cnt += 1 
    #         # conn.close()

    # return db_insert_cnt

get_mt20id('2023-08-29')

# import requests

# def atat(id):
#     SERVICE_KEY='1622ef3e02314464a8bfba2ea8345e15'
#     print(SERVICE_KEY, id)
#     url = f"http://kopis.or.kr/openApi/restful/pblprfr/{id}?service={SERVICE_KEY}"
#     result = urlopen(url)
#     data = bs(result, 'lxml-xml')
#     print(data)

# atat('PF179943')
