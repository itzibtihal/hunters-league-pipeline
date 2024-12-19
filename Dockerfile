# Use an OpenJDK base image
FROM openjdk:17-jdk-slim
LABEL authors="soulaiman"

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build output
# Replace `samurai.jar` with the actual name of your JAR file
ARG JAR_FILE=target/hunters_league-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Copy the SSL keystore
# Ensure `baeldung.p12` exists in the specified directory
COPY src/main/resources/baeldung.p12 /app/baeldung.p12

# Expose the server port
EXPOSE 8443

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Add JVM options if needed
# Example:
# ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
