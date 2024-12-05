# Use the official OpenJDK image as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the JAR file and skip tests
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR file to the container (update the path if necessary)
COPY target/sender-0.0.1-SNAPSHOT.jar /app/sender.jar

# Expose the port the app will run on
EXPOSE 8082

# Expose port for Kafka tools (optional, in case you want to run Kafka commands directly)
EXPOSE 9092

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/sender.jar"]
