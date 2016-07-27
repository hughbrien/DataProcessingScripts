#!/bin/sh

DIR=`dirname $0`
SESSION_FILE=${DIR}/session.dat
USER=admin
PASSWORD=admin
HOST=127.0.0.1
PORT=8090
APP_NAME="DemoApplication"

#authenticate and output the session id to ${SESSION_FILE}
curl -s -c ${SESSION_FILE} --user ${USER}@customer1:${PASSWORD} -X GET http://${HOST}:${PORT}/controller/auth?action=login

EVENT_PARAMS="summary=LoadTest/Ramp/Start&comment=Load_Test_Workload_XYZ_Ramp-Up_Start&eventtype=CUSTOM"
URI=http://${HOST}:${PORT}/controller/rest/applications/First%20Data/events

echo curl -s -b ${SESSION_FILE} --data "${EVENT_PARAMS}" ${URI}
curl -s -b ${SESSION_FILE} --data "${EVENT_PARAMS}" ${URI}

