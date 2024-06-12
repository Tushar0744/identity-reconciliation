#!/bin/bash

# Exit on any error
set -e

# Build the Spring Boot application
./mvnw clean install

# Define the JAR file name
JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

# Check if the JAR file was created
if [ ! -f "$JAR_FILE" ]; then
  echo "Error: JAR file not found!"
  exit 1
fi

# Build the Docker image
docker build -t identity-app-image .

# Optionally push to a Docker registry
# docker tag your-application-image your-dockerhub-username/your-application-image:latest
# docker push your-dockerhub-username/your-application-image:latest

echo "Build and Docker image creation completed successfully."
