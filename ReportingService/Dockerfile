FROM maven:3.8.4-openjdk-17 AS build

EXPOSE 8761
WORKDIR /app

COPY . /app
RUN mvn package

FROM openjdk:17-alpine
WORKDIR /app

#ENV SPRING_PROFILES_ACTIVE default
ENV SERVER.PORT 8787

COPY --from=build /app/target/ReportingService-1.0.0.jar ReportingService-1.0.0.jar

ENTRYPOINT ["java", "-jar", "ReportingService-1.0.0.jar"]


## Stage 1: Build the application
#FROM maven:3.9.4 AS build
#WORKDIR /app
#
#COPY pom.xml .
#RUN mvn -B dependency:resolve-plugins dependency:resolve
#
#COPY . .
#RUN mvn clean package
#
## Stage 2: Create a minimal JRE runtime image
#FROM azul/zulu-openjdk:17
#
## Create a non-root user
#RUN groupadd -r spring && useradd -r -g spring spring
#USER spring:spring
#
## Copy the JAR file from the build stage to the new image
#COPY --from=build /app/target/*.jar /app/ReportingService.jar
#
#EXPOSE 8787
#
#ENV SPRING_PROFILES_ACTIVE default
#ENV SERVER.PORT 8787
#
## Command to run the application
#CMD ["java", "-jar", "/app/ReportingService.jar"]
