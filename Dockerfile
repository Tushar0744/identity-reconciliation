# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim
RUN mkdir /temp

# Set the working directory
WORKDIR /app

# Copy the local JAR file to the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app

ARG PROPERTIES_PATH=src/main/resources/
RUN echo $PROPERTIES_PATH
COPY $PROPERTIES_PATH/application.properties /temp/application.properties

# Make port 8080 available to the world outside this container
EXPOSE 8080

ENV JAVA_OPTS=" -Dexternal.property.resource=/temp/ -Dspring.config.location=/temp/"

# Run the JAR file
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT exec java -jar $JAVA_OPTS demo-0.0.1-SNAPSHOT.jar


