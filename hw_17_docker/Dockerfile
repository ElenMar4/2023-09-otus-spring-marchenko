FROM openjdk:17.0.2-oracle
COPY /target/hw_17_docker-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]