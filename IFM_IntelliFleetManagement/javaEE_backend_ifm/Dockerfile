# Dockerfile for Java Spring Tomcat
# Base image
FROM openjdk:20-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the application jar file to the container
COPY target/ifm-0.0.1-SNAPSHOT.war /app/ifm.war

# Expose port 8080 for Tomcat
EXPOSE 8080

# Start Tomcat and deploy the application
CMD ["java", "-jar", "/app/ifm.war"]