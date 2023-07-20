FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/consumer-1.0.0.jar /app

EXPOSE 20001
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/consumer-1.0.0.jar"]