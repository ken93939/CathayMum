from flask import Flask,request
import json,pandas
from datetime import datetime,timedelta
app = Flask(__name__)
@app.route('/app/pcheck',methods=['POST'])

def job():
	results = {}
	try:
		name = request.form['name']
		ticket_no = request.form['ticket_no']
		queue_list = pandas.read_csv('./data/checkin_list.csv')
		last_one = queue_list.iloc[-1]
		new_queue_no = int(last_one['queue_no'] +1)
		results['queue_no'] = new_queue_no
		last_one_time = datetime.strptime(str(last_one['eta']), "%Y-%m-%d %H:%M:%S")
		new_one_time = str(last_one_time+timedelta(minutes=1))
		results['eta'] = new_one_time
		new_data = {'name':name, 'ticket_no':ticket_no,'eta':new_one_time,'queue_no':new_queue_no}
		new_queue_list = queue_list.append(new_data, ignore_index=True)
		new_queue_list.to_csv('./data/checkin_list.csv',index=None)
		results['status'] = 'success'
	except Exception as e:
		results = {}
		results['status'] = 'fail'
		results['reason'] = 'Incorrect Information'
		results = json.dumps(results)
		return results

	results = json.dumps(results)
	return results


if __name__ == '__main__':
	app.run(host='localhost',port=8001,debug=True)
