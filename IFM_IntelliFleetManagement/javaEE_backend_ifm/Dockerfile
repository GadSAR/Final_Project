# Dockerfile for Java Spring Tomcat
FROM tomcat:11.0.0-M5-jdk17-temurin-jammy

# Copy the application war file to the container
COPY target/ifm-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

# Expose port 8080 for Tomcat
EXPOSE 8080

# Start Tomcat and deploy the application
CMD ["catalina.sh", "run"]
