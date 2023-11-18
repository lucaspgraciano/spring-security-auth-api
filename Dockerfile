FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/authentication-1.0.0.jar authentication-1.0.0.jar
EXPOSE 8000
CMD ["java", "-jar", "authentication-1.0.0.jar"]