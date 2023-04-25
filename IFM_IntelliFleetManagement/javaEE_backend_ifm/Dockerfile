# Dockerfile for backend-ifm project

# Use an official OpenJDK runtime as a parent image
FROM openjdk:20-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the project jar file to the container
COPY target/ifm-0.0.1-SNAPSHOT.war /app/ifm.war

# Expose port 8080 for the container
EXPOSE 8080

# Run the jar file when the container launches
CMD ["java", "-jar", "ifm.war"]