#!/bin/sh

export DOCKER_HOST_IP=$(curl --retry 3 --connect-timeout 2 --max-time 2  -s 169.254.169.254/latest/meta-data/public-ipv4)
echo "$DOCKER_HOST_IP"

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
echo "Waiting for the eureka server to start on port 8761"
echo "********************************************************"
# ugly ass hack
eureka_uri_var=`echo $EUREKA_URI`
eureka_uri_length=`echo ${#eureka_uri_var}`
eureka_uri_no_port=`echo ${eureka_uri_var:7:eureka_uri_length-19}`
while ! `nc -z "$eureka_uri_no_port" 8761`; do sleep 3; done
echo "*******  Eureka Server has started"


echo "********************************************************"
echo "Starting Gateway Service with Configuration Service via Eureka :  $EUREKA_URI"
echo "Using Profile: $PROFILE"
echo "********************************************************"
java -Dserver.port=8086   \
-Dspring.profiles.active=$PROFILE -jar /app.jar