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
docker build -t wdm/users users/
docker build -t wdm/stocks stocks/
docker build -t wdm/payments payments/
docker build -t wdm/orders orders/
docker build -t wdm/config config-service/
docker build -t wdm/eureka eureka