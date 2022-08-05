FROM eclipse-temurin:8-jdk-alpine

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add wget unzip tcpdump ngrep iproute2-ss bind-tools

WORKDIR /app
COPY /target/gateway-1.0.0.jar /app

EXPOSE 20000
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/gateway-1.0.0.jar"]