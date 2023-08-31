# syntax=docker/dockerfile:1.4

FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:8-extended-ga-centos

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

COPY ./target/C.jar /app/target/C.jar

EXPOSE 20003
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/target/C.jar"]
