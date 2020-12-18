FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/sc-C-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/sc-C-0.0.1-SNAPSHOT.jar"]