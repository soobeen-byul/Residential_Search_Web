from airflow import DAG
from datetime import datetime, timedelta
from airflow.operators.bash import BashOperator
from airflow.operators.empty import EmptyOperator
from airflow.operators.python import BranchPythonOperator, PythonOperator
from airflow.models.variable import Variable
import pendulum
import requests

local_tz = pendulum.timezone("Asia/Seoul")
SERVER_API = Variable.get("SERVER_API")

default_args = {
    'owner': 'sms',
    'depends_on_past': False,
    'start_date': datetime(2023, 8, 28, tzinfo=local_tz),
    'retries': 0,
}

dag = DAG(
    'get-kopis-api',
    default_args=default_args,
    schedule="0 1 * * 1", #매주 월요일 1AM
    user_defined_macros={'local_dt': lambda execution_date: execution_date.in_timezone(local_tz).strftime("%Y-%m-%d %H:%M:%S")},
)

#DB 적재
def load_db(execution_date,**context):
    exe_dt=execution_date.in_timezone('Asia/Seoul').strftime('%Y-%m-%d')
    api_url = f"http://{SERVER_API}/kopis/performance-to-db?date={exe_dt}"

    response = requests.get(api_url).json()
    print(api_url)
    print(f"{exe_dt}일, {response}건 performance DB 적재 완료")

    return response

def get_detail(execution_date):
    exe_dt=execution_date.in_timezone('Asia/Seoul').strftime('%Y-%m-%d')
    api_url = f"http://{SERVER_API}/kopis/information?date={exe_dt}"
    request = requests.get(api_url).json()

    print(api_url)
    print(f"{exe_dt}일, {request} 공연상세정보 적재 완료")
    print(request)

#정합성  체크
def check_logic(execution_date,**context):
    exe_dt=execution_date.in_timezone('Asia/Seoul').strftime('%Y-%m-%d')
    DB_CNT=context['task_instance'].xcom_pull(task_ids='Load.Kopis_to_DB')
    api_url = f"http://{SERVER_API}/check/kopis?st_dt={exe_dt}&db_cnt={DB_CNT}"
    request = requests.get(api_url)

    if request == 1 :
        return "ERROR"
    
    else:
        return "DONE"


start = EmptyOperator(task_id = 'Stark.task', dag = dag)
finish = EmptyOperator(task_id = 'Finish.task', dag = dag)

load_to_db = PythonOperator(task_id="Load.Kopis_to_DB",
                            python_callable=load_db,
                            dag=dag)

save_detail = PythonOperator(task_id="Save.Kopis_Detail",
                            python_callable=get_detail,
                            dag=dag)

branching = BranchPythonOperator(task_id='Check.logic',
                                 python_callable=check_logic,
                                 dag=dag)

error = EmptyOperator(task_id = 'ERROR', dag = dag)
done = EmptyOperator(task_id = 'DONE', dag = dag)


start >> load_to_db >> save_detail >> branching
branching >> error >> finish
branching >> done  >> finish



