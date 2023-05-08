
FROM tomcat:11.0.0-M5-jdk17-temurin-jammy

COPY target/ifm-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
