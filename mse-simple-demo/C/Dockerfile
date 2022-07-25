FROM eclipse-temurin:8-jdk-alpine

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add wget unzip tcpdump ngrep iproute2-ss bind-tools

WORKDIR /app
COPY /target/C-1.0.0.jar /app

EXPOSE 20003
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/C-1.0.0.jar"]