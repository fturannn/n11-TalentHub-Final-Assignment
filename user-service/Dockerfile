FROM openjdk:21
VOLUME /tmp
EXPOSE 8081
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]