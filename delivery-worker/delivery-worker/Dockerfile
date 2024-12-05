# Use the official OpenJDK image as the base image
FROM openjdk:21-jdk-slim

# Install necessary dependencies for Kafka tools (no need for Kafka installation)
RUN apt-get update && apt-get install -y wget tar

# Set the working directory inside the container
WORKDIR /app

# Copy the source code to the container (optional, depends on your setup)
COPY . .

# Build the JAR file and skip tests (this is a redundant step if you're copying the pre-built JAR directly)
RUN ./mvnw clean package -DskipTests

# Copy the pre-built JAR file to the container (ensure the path is correct)
COPY target/delivery-worker-0.0.1-SNAPSHOT.jar /app/delivery-worker.jar

# Expose port 8080 for the application (update if needed based on your app configuration)
EXPOSE 8081

# Expose port for Kafka tools (optional, in case you want to run Kafka commands directly)
EXPOSE 9092

# Run the JAR file
ENTRYPOINT ["java", "-jar", "delivery-worker.jar"]
