FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/devops-v2.jar devops-v2.jar
ENTRYPOINT ["java", "-jar", "devops-v2.jar"]
