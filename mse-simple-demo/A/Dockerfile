FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /target/A-1.1.0.jar /app

EXPOSE 20001
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/A-1.1.0.jar"]
