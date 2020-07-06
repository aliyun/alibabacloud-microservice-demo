FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /adservice-provider/target/adservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/adservice-provider-0.0.1-SNAPSHOT.jar"]
