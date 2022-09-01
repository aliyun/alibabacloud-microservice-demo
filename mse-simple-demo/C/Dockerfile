FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:17

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /target/C-1.1.0.jar /app

EXPOSE 20003
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/C-1.1.0.jar"]