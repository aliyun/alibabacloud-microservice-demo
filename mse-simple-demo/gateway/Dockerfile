FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:8-extended-ga-centos

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /target/gateway-1.0.0.jar /app

EXPOSE 20000
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/gateway-1.0.0.jar"]