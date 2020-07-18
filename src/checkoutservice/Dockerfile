FROM openjdk:8-jre-alpine
WORKDIR /app
COPY /checkoutservice-provider/target/checkoutservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar -Xms1024m -Xmn500m /app/checkoutservice-provider-0.0.1-SNAPSHOT.jar"]
