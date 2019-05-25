#!/bin/sh

echo "********************************************************"
echo "Waiting for the configuration server to start on port 8888"
echo "********************************************************"
while ! `nc -z config-service 8888`; do sleep 3; done
echo "*******  Configuration Server has started"


echo "********************************************************"
echo "Starting Eureka Server"
echo "********************************************************"
java -Dserver.port=8761 -jar /app.jar
