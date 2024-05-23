FROM openjdk:17-jdk

WORKDIR /app

COPY target/delivery-challenge-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8083

ENTRYPOINT ["java","-jar","app.jar"]