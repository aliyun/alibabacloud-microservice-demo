FROM  openjdk:8-jre-alpine
WORKDIR /app
COPY /checkoutservice-provider/target/checkoutservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -Dcom.sun.management.jmxremote.port=19991 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar /app/checkoutservice-provider-0.0.1-SNAPSHOT.jar"]
