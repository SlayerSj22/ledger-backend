# Use Java 21 base image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/ledger-0.0.1-SNAPSHOT.jar"]