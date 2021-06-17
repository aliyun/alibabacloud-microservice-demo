FROM registry.cn-beijing.aliyuncs.com/edas_unified/jct_dw_8_4_4:0.1

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /productservice-provider/target/productservice-provider-1.0.0-SNAPSHOT.jar /app
COPY /start.sh /app

EXPOSE 8080
CMD ["/app/start.sh"]
