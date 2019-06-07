#!/bin/sh

echo "********************************************************"
echo "Waiting for the configuration server to start on port 8888"
echo "********************************************************"
# ugly ass hack
config_uri_var=`echo $CONFIG_URI`
config_uri_length=`echo ${#config_uri_var}`
config_uri_no_port=`echo ${config_uri_var:7:config_uri_length-12}`
while ! `nc -z "$config_uri_no_port" 8888`; do sleep 3; done
echo "*******  Configuration Server has started"


echo "********************************************************"
echo "Starting Eureka Server"
echo "********************************************************"
java -Dserver.port=8761 -jar /app.jar
