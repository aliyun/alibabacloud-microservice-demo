FROM flystar32/mse-base:latest
WORKDIR /app
COPY /target/gateway-1.0.0.jar /app

EXPOSE 20000
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/gateway-1.0.0.jar"]