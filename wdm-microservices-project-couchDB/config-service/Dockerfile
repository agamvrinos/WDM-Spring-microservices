FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
RUN mkdir -p /root/.ssh
RUN chmod 700 /root/.ssh
RUN apk update && apk add openssh
RUN ssh-keyscan github.com > /root/.ssh/known_hosts
ENTRYPOINT ["java","-jar","/app.jar"]