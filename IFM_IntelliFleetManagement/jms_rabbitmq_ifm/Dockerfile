# Use an official OpenJDK runtime as a parent image
FROM openjdk:20-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Run Maven to build the application
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

# Copy the current directory contents into the container at /app
COPY src ./src

# Expose port 8081 for the application
EXPOSE 8081

# Run the application when the container starts
CMD ["./mvnw", "spring-boot:run"]
