FROM eclipse-temurin:8-jdk-alpine

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

WORKDIR /app
COPY /target/B-1.0.0.jar /app

EXPOSE 20002
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/B-1.0.0.jar"]