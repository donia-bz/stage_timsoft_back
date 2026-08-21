FROM maven:3.9-eclipse-temurin-17 AS build
ARG SERVICE_NAME
WORKDIR /app
COPY pom.xml .
COPY ${SERVICE_NAME} ${SERVICE_NAME}
WORKDIR /app/${SERVICE_NAME}
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
ARG SERVICE_NAME
WORKDIR /app
COPY --from=build /app/${SERVICE_NAME}/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
