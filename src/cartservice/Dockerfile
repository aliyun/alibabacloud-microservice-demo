FROM dragonwell-registry.cn-hangzhou.cr.aliyuncs.com/dragonwell/dragonwell:8-extended-ga-centos

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

WORKDIR /app
COPY /cartservice-provider/target/cartservice-provider-1.0.0-SNAPSHOT.jar /app
COPY /start.sh /app

EXPOSE 8080
CMD ["/app/start.sh"]