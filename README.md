# WDM-Spring-microservices


### Docker and Deployment notes

requirements: Docker installed locally

#### running whole system as containers locally

1. switch current working directory to respective project folder
2. In order to build docker images of all services run
the build.sh in the top level of the project. This will 
create images and save them locally.
3. In order to run locally use the following command:
"docker-compose up". This may not work on linux due
to the way the localhost ip is resolved (needed to 
contact the couchdb/postgres instance which is not running
in its own container).

#### publish images to docker hub
1. retag images to user/repo:service
2. docker login
3. docker push user/repo

Then when creating task definitions for ECS refer to the 
correct user/repo (needs to be public).
