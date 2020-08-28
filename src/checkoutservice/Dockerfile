FROM registry.cn-beijing.aliyuncs.com/edas_unified/jct_dw_8_4_4:0.1
WORKDIR /app
COPY /checkoutservice-provider/target/checkoutservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -Dcom.sun.management.jmxremote.port=19991 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar /app/checkoutservice-provider-0.0.1-SNAPSHOT.jar"]
