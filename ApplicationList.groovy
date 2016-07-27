
// global map to hold some config
globals = [:]

def applicationNames = ["CCII-CIH", "CCII-EXT", "CCII-GUI", "CCII-MSG", "CCII-SE"]


String credentials = 'user1@customer1' + ':' + 'xyzzzzzzzz'
globals.auth = 'Basic ' + credentials.bytes.encodeBase64().toString()
globals.baseURL = 'http://demo3.appdynamics.com' + '/controller/rest/applications/'


def POLICY_VIOLATIONS = '/controller/rest/applications/CCII/problems/policy-violations?time-range-type=BEFORE_NOW&duration-in-mins=60&output=XML'

EVENTS = 'http://demo3.appdynamics.com/controller/rest/applications/CCII-MSG/events?time-range-type=BEFORE_NOW&duration-in-mins=5&event-types=%20APPLICATION_ERROR,DIAGNOSTIC_SESSION&severities=INFO,WARN,ERROR&output=XML' 


println credentials
println globals 

// list out all applications
def apps = getApplicationErrors()
println apps.getClass()
println apps


applicationNames.each{
	appName -> 
	println(appName)
}




apps.keySet().each {
	key ->
	println("$key : ${apps.get(key)}")
}


// Get XML from given URL
// returns parsed XML
def getXMLfromURL(String url)
{
	def buffer = new StringBuilder(url)
	buffer.append(url.contains('?') ? '&' : '?').append('output=xml')

	def connection = new URL(buffer.toString()).openConnection()
	connection.setRequestProperty('Authorization', globals.auth)

	def content = new XmlParser().parse(connection.content)

	return content
}

// Get all applications
// returns a map of matching application id to name
def getApplications()
{
	def appMap = [:]

	String url = globals.baseURL
	println url
	def content = getXMLfromURL(url)
	println content.getClass()
	println content.name()
	println content.application.size()
	content.application.each{
		app -> 
			print app.id.text()
			print " : "
			println app.name.text()
			
	}
	
	return appMap
}



def getApplicationErrors()
{
	def appMap = [:]

	String url = EVENTS
	println url
	def events = getXMLfromURL(url)
	println events.getClass()
	println events.name()
	println events.event.size()
	events.event.each{
		xmlEvent -> 
			println "# # # # # # # # Error # # # # # # # "
			println xmlEvent.id.text() 
			println xmlEvent.type.text()
			println xmlEvent.subType.text()
			println xmlEvent.eventTime.text()
			println xmlEvent.severity.text()
			println xmlEvent.summary.text()
			println ""
	}
	return appMap
}

// get tiers for application
// returns a map of tier id to name
def getTiers(String appID)
{
	def tierMap = [:]

	String url = globals.baseURL + appID + '/tiers'
	def content = getXMLfromURL(url)
	content.tier.each
	{
		tier ->
		tierMap.(tier.id.text()) = tier.name.text()
	}

	return tierMap
}

// get the Business Transactions for application
// returns a map of BT id to name
def getBizTxn(String appID)
{
	def biztxnMap = [:]

	String url = globals.baseURL + appID + '/business-transactions'
	def content = getXMLfromURL(url)
	content.'business-transaction'.each
	{
		bt ->
		biztxnMap.(bt.id.text()) = bt.name.text()
	}

	return biztxnMap
}

// get the nodes for application
// returns a map of node id to name
def getNodes(String appID)
{
	def nodeMap = [:]

	String url = globals.baseURL + appID + '/nodes'
	def content = getXMLfromURL(url)
	content.node.each
	{
		node ->
		nodeMap.(node.id.text()) = node.name.text()
	}

	return nodeMap
}
