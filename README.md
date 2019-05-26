# WDM-Spring-microservices


### Docker and Deployment
(atm only in postgres project)

requirements: Docker installed locally

In order to build docker images of all services run
the build.sh in the top level of the project. This will 
create images and save them locally.

In order to run locally use the following command:
"docker-compose up" from the same folder as the 
docker-compose file. This may not work on linux due
to the way the localhost ip is resolved (needed to 
contact the postgres instance which is not running
in its own container).