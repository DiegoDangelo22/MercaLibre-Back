FROM amazoncorretto:20-alpine-jdk
MAINTAINER DangeloDiego
COPY target/back-0.0.1-SNAPSHOT.jar mercalibre.jar
ENTRYPOINT ["java","-jar","/mercalibre.jar"]