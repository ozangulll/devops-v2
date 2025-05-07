FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/devops-v2-updated.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
