
FROM eclipse-temurin:20-jre-alpine

COPY target/jms_rabbitmq_ifm-0.0.1-SNAPSHOT.jar myapp.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/myapp.jar"]
