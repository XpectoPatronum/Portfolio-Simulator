# Use a minimal Java runtime
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/PortfolioSimulator-0.1-all.jar app.jar

# Expose the port Micronaut runs on (default: 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
