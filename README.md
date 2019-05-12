# WDM-Spring-microservices

## Cloud config service
For now we need to manually change the application.properties of the config service to point to the local git directory that contains the configuration files for the other services. So you need to create some directory and git init it with the approriate config files in them. I included the userservice config in the top level of our github for now.

Then start the config service first, after which the other services can be started. Within the other services we need a bootstrap.properties file that points to the correct port of the config service. This should later be done by commandline args or env vars. Also the name of the microservice given in the bootstrap.properties needs to match the file name of its config file in the git config directory.
