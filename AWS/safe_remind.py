from twilio.rest import TwilioRestClient 
from flask import Flask,request
import json
# put your own credentials here 
ACCOUNT_SID = "AC66577f03e6cf9eeccd5ba2ba0d4df11a" 
AUTH_TOKEN = "bf54f901440ab416e07c250fc22402fd" 
 
client = TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN) 

app = Flask(__name__)
@app.route('/app/sms',methods=['GET','POST'])

def progress():
	global client
	results = {}
	if request.method == 'POST':
		try:
			client.messages.create(
				to="+85294252600", 
				from_="+19287568483 ", 
				body="I have just finish my Cathay Pacific Flight and safely arrived on Australia. I will be back in days.", 
			)
			results['status'] = 'success'
		except:
			results['status'] = 'fail'
			results = json.dumps(results)
			return results
	results = json.dumps(results)
	return results


if __name__ == '__main__':
	app.run(host='localhost',port=8001,debug=True)
