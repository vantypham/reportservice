FROM maven:3.8.4-openjdk-17 AS build

EXPOSE 8088
WORKDIR /app

COPY ./ReportingService /app
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app

COPY --from=build /app/target/ReportingService-0.0.1-SNAPSHOT.jar ReportingService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "ReportingService-0.0.1-SNAPSHOT.jar"]
