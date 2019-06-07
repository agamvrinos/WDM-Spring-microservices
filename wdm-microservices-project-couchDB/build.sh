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
docker tag wdm-couch/config jannest/wdm-couch:config
docker tag wdm-couch/eureka jannest/wdm-couch:eureka
docker tag wdm-couch/gateway jannest/wdm-couch:gateway
docker tag wdm-couch/orders jannest/wdm-couch:orders
docker tag wdm-couch/payments jannest/wdm-couch:payments
docker tag wdm-couch/stocks jannest/wdm-couch:stocks
docker tag wdm-couch/users jannest/wdm-couch:users

