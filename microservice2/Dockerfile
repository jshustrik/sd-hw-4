FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8081:8081
COPY target/OrderMicroservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]