FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/zuul-gateway-1.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/zuul-gateway-1.0-SNAPSHOT.jar"]
