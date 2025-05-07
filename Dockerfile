FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/devops-v2-updated.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
