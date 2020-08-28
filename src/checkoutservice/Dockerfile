FROM registry.cn-beijing.aliyuncs.com/edas_unified/jct_dw_8_4_4:0.1
WORKDIR /app
COPY /checkoutservice-provider/target/checkoutservice-provider-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar -Xms1024m -Xmn500m /app/checkoutservice-provider-0.0.1-SNAPSHOT.jar"]
