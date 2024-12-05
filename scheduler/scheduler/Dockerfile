# Use the official OpenJDK image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the JAR file and skip tests
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR file to the container (update the path if necessary)
COPY target/scheduler-0.0.1-SNAPSHOT.jar email-scheduler-service.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "email-scheduler-service.jar"]
