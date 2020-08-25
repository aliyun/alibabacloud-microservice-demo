FROM registry.cn-beijing.aliyuncs.com/edas_unified/jct_dw_8_4_4:0.1
WORKDIR /app
COPY /target/frontend-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/frontend-1.0.0-SNAPSHOT.jar"]