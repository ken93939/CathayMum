import requests,json
import pprint,random
def main():
	global vm_ip
	vm_ip='http://35.160.199.205'
	#pcheck()
	order()
	order_end()
	#sms()
	#notice()
	return

def	pcheck():
	body = {'name':'chan tai man','ticket_no':'1123ABC'}
	url = vm_ip +'/app/pcheck'
	r = requests.post(url,data=body)
	post(r)
	return

def order():
	seat_no = '%dA' %(random.randint(10, 20))
	type = ['food','blanket','others']
	order_type = type[random.randint(0,len(type)-1)]
	body = {'order_type':order_type,'seat_no':seat_no} 
	url = vm_ip +'/app/order'
	r = requests.post(url,data=body)
	post(r)
	return

def order_end():
	body = {'seat_no':'28A'}
	url = vm_ip +'/app/order'
	r = requests.get(url,params=body)
	post(r)
	return

def sms():
	url = vm_ip +'/app/sms'
	r = requests.post(url)
	post(r)
	return

def notice():
	url = 'https://fcm.googleapis.com/fcm/send'
	params = {'key':'AIzaSyBPlZHLM5K21TiS01yCpigYZqpeTYhBbe4'}
	not_body = { 	"notification": { "title": "Borading Notice", "body": "10 minutes left for boarding flight CX502 at counter 55"} ,
				  	"to" : "1:695415572596:android:9c54d2e971f1d5f9"}	
	r = requests.post(url,headers=params,json=not_body)
	print r.content
	print r.url
	post(r)
	return
def post(req):
	r_code = int(req.status_code)
	print '\n','http',r_code
	if r_code==200:
		print (json.loads(req.content))
	return

main()