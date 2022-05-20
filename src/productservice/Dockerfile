FROM eclipse-temurin:8-jdk-alpine

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /productservice-provider/target/productservice-provider-1.0.0-SNAPSHOT.jar /app
COPY /start.sh /app

EXPOSE 8080
CMD ["/app/start.sh"]
