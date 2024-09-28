
#FROM openjdk:21-jdk-slim
#
#ARG JAR_FILE=target/*.jar
#
#WORKDIR /app
#
#COPY ${JAR_FILE} /app/FFVC.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","/app/FFVC.jar"]


# Stage 1: Build the application
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create the final image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port your application runs on
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]