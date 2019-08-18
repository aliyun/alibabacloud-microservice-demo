FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /cartservice-provider/target/cartservice-provider-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/cartservice-provider-1.0.0-SNAPSHOT.jar"]