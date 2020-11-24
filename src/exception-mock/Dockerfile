FROM  openjdk:8-jre-alpine
WORKDIR /app
COPY /exception-mock-provider/target/exception-mock-provider-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -server -Dcom.sun.management.jmxremote.port=19991 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar /app/exception-mock-provider-1.0.0-SNAPSHOT.jar"]