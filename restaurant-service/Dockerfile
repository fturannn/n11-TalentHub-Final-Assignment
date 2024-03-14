FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/restaurant-service-0.0.1-SNAPSHOT.jar restaurant-service.jar
ENTRYPOINT ["java","-jar","/restaurant-service.jar"]

#FROM maven:3.8.3-openjdk-17 as build
#WORKDIR /app
#COPY . /app/
#RUN mvn clean package
#
#FROM openjdk:17-jdk
#COPY --from=build /app/target/restaurant-service-0.0.1-SNAPSHOT.jar /app/restaurant-service.jar
#CMD java -jar /app/restaurant-service.jar