mvn package
docker build -t wdm/users users/
docker build -t wdm/stocks stocks/
docker build -t wdm/payments payments/
docker build -t wdm/orders orders/
docker build -t wdm/config config-service/