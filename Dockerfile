FROM openjdk:17-jdk-temurin
WORKDIR /app
COPY target/devops-v2-updated.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
