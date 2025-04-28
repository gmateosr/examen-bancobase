# Use the official OpenJDK base image
FROM eclipse-temurin:17

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/examen-bancobase-0.0.1-SNAPSHOT.jar app_transaction.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app_transaction.jar"]