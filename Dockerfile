# Use the official Maven image to create a build artifact.
# Start by specifying the base image
FROM maven:3.6.3-jdk-8 AS build

# Set the current working directory inside the image
WORKDIR /app

# Copy the pom.xml file and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code and compile it
COPY src ./src
RUN mvn clean package

# Use the official OpenJDK image to run the application
FROM openjdk:8-jdk-alpine

# Set the current working directory inside the image
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
