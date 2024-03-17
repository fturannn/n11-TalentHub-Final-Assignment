FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/restaurant-service-0.0.1-SNAPSHOT.jar restaurant-service.jar
ENTRYPOINT ["java","-jar","/restaurant-service.jar"]