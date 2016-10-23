from routes import Mapper
from pcheck import app as app1
from order import app as app2
from safe_remind import app as app3
class multiapp(object):
	def __init__(self):
		self.map = Mapper()
		self.map.connect('app1', '/app/pcheck', app=app1)
		self.map.connect('app2','/app/order',app=app2)
		self.map.connect('app3','/app/sms',app=app3)

	def __call__(self, environ, start_response):
		match = self.map.routematch(environ=environ)
		if not match:
			return self.error404(environ, start_response)
		return match[0]['app'](environ, start_response)

	def error404(self, environ, start_response):
		html = b"""\
		<html>
		  <head>
			<title>404 - Not Found</title>
		  </head>
		  <body>
			<h1>404 - Not Found</h1>
		  </body>
		</html>
		"""
		headers = [
			('Content-Type', 'text/html'),
			('Content-Length', str(len(html)))
		]
		start_response('404 Not Found', headers)
		return [html]

multiapp = multiapp()