FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/provider-1.0.0.jar /app

EXPOSE 20002
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/provider-1.0.0.jar"]