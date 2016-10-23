from flask import Flask,request
import json,time,pandas

app = Flask(__name__)
@app.route('/app/order',methods=['GET','POST'])

def progress():
	results = {}
	order_queue = pandas.read_csv('./data/order_list.csv')
	if request.method == 'POST':
		try:
			new_record = {'order_status':'unfinish'}
			new_record['time'] = time.strftime("%H:%M:%S")
			new_record['order_type'] = request.form['order_type']
			new_record['seat_no'] = request.form['seat_no']
			order_queue = order_queue.append(new_record,ignore_index=True)
			results['status'] = 'success'
		except:
			results['status'] = 'fail'
			results = json.dumps(results)
			return results
	elif request.method =='GET':
		seat_no = request.args['seat_no']
		order_queue.ix[order_queue['seat_no'] == seat_no, 'order_status'] = 'finshed'
		results['status'] = 'success' 
	order_queue.to_csv('./data/order_list.csv',index=None)
	results = json.dumps(results)
	return results


if __name__ == '__main__':
	app.run(host='localhost',port=8001,debug=True)