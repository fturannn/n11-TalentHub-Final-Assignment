FROM openjdk:21
VOLUME /tmp
EXPOSE 8082
COPY target/logger-0.0.1-SNAPSHOT.jar logger.jar
ENTRYPOINT ["java","-jar","/logger.jar"]