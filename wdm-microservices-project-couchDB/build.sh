cd config-service
mvn package
cd -
cd orders
mvn package
cd -
cd payments
mvn package
cd -
cd stocks
mvn package
cd -
cd users
mvn package
cd -
cd eureka
mvn package
cd -
cd gateway
mvn package
cd -
docker build -t wdm-couch/users users/
docker build -t wdm-couch/stocks stocks/
docker build -t wdm-couch/payments payments/
docker build -t wdm-couch/orders orders/
docker build -t wdm-couch/config config-service/
docker build -t wdm-couch/eureka eureka/
docker build -t wdm-couch/gateway gateway/

