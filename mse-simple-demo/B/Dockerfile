FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:11

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /target/B-1.1.0.jar /app

EXPOSE 20002
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/B-1.1.0.jar"]