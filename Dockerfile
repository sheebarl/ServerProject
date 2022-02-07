# syntax=docker/dockerfile:1
FROM openjdk:17
WORKDIR /ServerProject
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8080
