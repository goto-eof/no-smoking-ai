FROM gradle:8.5-jdk21-alpine AS builder

WORKDIR /app

COPY no-smoking-java-common no-smoking-java-common
COPY no-smoking-java-server no-smoking-java-server
COPY settings.gradle settings.gradle
COPY gradle gradle
COPY gradlew gradlew
COPY no-smoking-java-server/src/main/resources/application.yml application.yml


RUN ./gradlew :no-smoking-java-server:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:+UseG1GC -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

WORKDIR /app
COPY --from=builder /app/no-smoking-java-server/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
