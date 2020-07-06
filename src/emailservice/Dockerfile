FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /emailservice-provider/target/emailservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/emailservice-provider-0.0.1-SNAPSHOT.jar"]
