FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /currencyservice-provider/target/currencyservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/currencyservice-provider-0.0.1-SNAPSHOT.jar"]
