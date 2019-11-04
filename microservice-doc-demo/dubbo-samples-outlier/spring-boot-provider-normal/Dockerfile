FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/spring-boot-provider-1.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/spring-boot-provider-1.0-SNAPSHOT.jar"]