# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Run Maven to build the application
RUN ./mvnw package -DskipTests

# Expose port 8080 for the application
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "target/P2010-0.0.1-SNAPSHOT.jar"]