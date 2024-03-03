FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
VOLUME /tmp


COPY target/*.jar app.jar

# Expose the port that your application runs on
EXPOSE 8080
# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar" ]