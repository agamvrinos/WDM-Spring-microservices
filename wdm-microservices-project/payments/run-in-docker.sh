#!/bin/sh

echo "********************************************************"
echo "Waiting for the configuration server to start on port 8888"
echo "********************************************************"
while ! `nc -z config-service 8888`; do sleep 3; done
echo "*******  Configuration Server has started"


echo "********************************************************"
echo "Waiting for the eureka server to start on port 8761"
echo "********************************************************"
while ! `nc -z eureka-service 8761`; do sleep 3; done
echo "******* Eureka Server has started"


echo "********************************************************"
echo "Starting Payments Service with Configuration Service via Eureka :  $EUREKA_URI"
echo "Using Profile: $PROFILE"
echo "********************************************************"
java -Dserver.port=8081   \
-Dspring.profiles.active=$PROFILE -jar /app.jar