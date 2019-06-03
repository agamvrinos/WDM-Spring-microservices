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
docker build -t wdm/users users/
docker build -t wdm/stocks stocks/
docker build -t wdm/payments payments/
docker build -t wdm/orders orders/
docker build -t wdm/config config-service/
docker build -t wdm/eureka eureka/
docker build -t wdm/gateway gateway/
docker tag wdm/config jannest/wdm:config
docker tag wdm/eureka jannest/wdm:eureka
docker tag wdm/gateway jannest/wdm:gateway
docker tag wdm/orders jannest/wdm:orders
docker tag wdm/payments jannest/wdm:payments
docker tag wdm/stocks jannest/wdm:stocks
docker tag wdm/users jannest/wdm:users
