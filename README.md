# Webshop Assignment - Spring microservices

RESTful microservices created using the Spring Boot framework for the application layer 
and PostgreSQL/CouchDB as a database. This project is done for TU Delft's course 
Web Data Management (IN4331) 2019.

### Architecture

<p align="center">
  <img src="https://github.com/agamvrinos/WDM-Spring-microservices/blob/master/images/Overview.png" height="241" width="601">
</p>

* **Configuration service:** Used to distribute the configuration flies to the instances
of the microservices. 
* **Discovery service (Eureka):**  Used for locating the services.
* **Gateway service (Zuul):**  Used for dynamic routing.
* **Load balancer (Ribbon - client side):** Automates the load balancing procedure in the client side in a dynamic way.
* **User microservice:** 
   * */users/create/* -> (POST - creates a user and returns an ID)
   * */users/remove/{user_id}* -> (DELETE - removes a user and return success/failure)
   * */users/find/{user_id}* -> (GET - returns the details of a user)
   * */users/credit/{user_id}* -> (GET - returns the current credit of a user)
   * */users/credit/subtract/{user_id}/{amount}* -> (POST - subtracts the amount from the credit of the user)
   * */users/credit/add/{user_id}/{amount}* -> (POST - subtracts the amount from the credit of the user)
* **Payment microservice:** 
    * */payment/pay/{user_id}/{order_id}* -> (POST - calls the user microservice in order to subtract the amount from the  user's credit)
    * */payment/status/{order_id}* -> (GET - returns the payment status)
* **Order microservice:**  
    * */orders/create/{user_id}* -> (POST - creates an order for the given user and returns an ID)
    * */orders/remove/{order_id}* -> (DELETE - deletes an order by ID)
    * */orders/find/{order_id}* -> (GET - retrieves the information of an order)
    * */orders/addItem/{order_id}/{item_id}* -> (POST - adds a given item in the order given)
    * */orders/removeItem/{order_id}/{item_id}* -> (DELETE - removes the given item from the given order)
    * */orders/checkout/{order_id}* -> (POST - makes the payment (via calling the payment service), subtracts the stock (via the stock service))
* **Stock microservice:** 
    * */stock/availability/{item_id}* -> (GET - returns an item’s availability)
    * */stock/add/{item_id}/{number}* -> (POST - increases the stock of a given item)
    * */stock/item/create/* -> (POST - adds an item, and returns its ID)
    * */stock/subtract* -> (POST - batch subtraction of stock given the item IDs)
    * */stock/addItems* -> (POST - batch addition of stock given the item IDs)

Our system is built to support the distributed transaction (checkout) where all the microservices need to communicate with each other as shown bellow (good weather setting). 

<p align="center">
 <img src="https://github.com/agamvrinos/WDM-Spring-microservices/blob/master/images/DistributedTransaction.png" height="383" width="536">
</p>

#### Consistency (SAGA-like) ####
The consistency of our system is based on the SAGA pattern of compensating actions 
upon failure. However, since the communication is synchronous due to the RESTful nature of 
our services the only way compensation action will be initiated is when a user does not have enough credit.
In that case the compensating action will be to add the items that were subtracted back to the stock.

#### Machine failures (journaling) ####
The way we decided to solve the case of machine failures is by journaling. Namely, we record every operation
that happens on the database. In case of a failure, if a transaction cannot be completed then
the system will either restore the records back to their original state or try again
since we assume that if a microservice instance fails another one will take its place almost
instantly. In that case we do not rollback but check if the changes have already been made
in the database so, we do not do them again. 

### Project structure 
#### Branches ####

*  [`master`](https://github.com/agamvrinos/WDM-Spring-microservices): This branch contains the main implementation without taking into 
    account machine failures.
* [`configuration-files`](https://github.com/agamvrinos/WDM-Spring-microservices/tree/configuration-files): Here we have all the configuration files that are used by the configuration server.
* [`master-consistency`](https://github.com/agamvrinos/WDM-Spring-microservices/tree/master-consistency): This branch contains the main implementation but, with the addition of database journaling that makes our implementation resistant to failures.
* [`locust-stress-test`](https://github.com/agamvrinos/WDM-Spring-microservices/tree/locust-stress-test): In this branch we have our implementation of the locust stress test.

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

### Team members

[Max van Deursen](https://github.com/MaxVanDeursen)

[Stavrangelos Gamvrinos](https://github.com/agamvrinos)

[Kyriakos Psarakis](https://github.com/kPsarakis)

[Panagiotis Soilis](https://github.com/psoilis)

[Jannes Timm](https://github.com/jannes-t)
