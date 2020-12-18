FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/sc-zuul-1.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/sc-zuul-1.0-SNAPSHOT.jar"]