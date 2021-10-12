FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /target/C-1.0.0.jar /app

EXPOSE 20003
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/C-1.0.0.jar"]