FROM registry.cn-beijing.aliyuncs.com/edas_unified/jct_dw_8_4_4:0.1
WORKDIR /app
COPY /productservice-provider/target/productservice-provider-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -Dcom.sun.management.jmxremote.port=19991 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar /app/productservice-provider-1.0.0-SNAPSHOT.jar"]
