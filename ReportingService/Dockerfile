# Stage 1: Build the application
FROM maven:3.9.4 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:resolve-plugins dependency:resolve

COPY . .
RUN mvn clean package

# Stage 2: Create a minimal JRE runtime image
FROM azul/zulu-openjdk:17

# Create a non-root user
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy the JAR file from the build stage to the new image
COPY --from=build /app/target/*.jar /app/ReportingService.jar

EXPOSE 8787
EXPOSE 443

ENV SPRING_PROFILES_ACTIVE default
ENV SERVER.PORT 8787

# Command to run the application
CMD ["java", "-jar", "/app/ReportingService.jar"]
