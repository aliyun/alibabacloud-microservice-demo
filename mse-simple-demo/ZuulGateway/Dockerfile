# syntax=docker/dockerfile:1.4

FROM maven:3-eclipse-temurin-8 as build

COPY <<EOF /root/.m2/settings.xml
<?xml version=\"1.0\"?>
<settings>
    <mirrors>
        <mirror>
            <id>alimaven</id>
            <name>aliyun maven</name>
            <url>https://maven.aliyun.com/repository/central</url>
            <mirrorOf>central</mirrorOf>
        </mirror>
    </mirrors>
</settings>
EOF

WORKDIR /app

COPY ./ ./

RUN --mount=type=cache,target=/root/.m2/repository/ \
    mvn clean package --batch-mode

FROM eclipse-temurin:8-jdk

RUN apt-get update && apt-get install -y \
    vim unzip netcat-openbsd telnet tcpdump iproute2 lsof ngrep \
    && rm -rf /var/lib/apt/lists/*

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

COPY --from=build /app/target/ZuulGateway.jar /app/target/ZuulGateway.jar
#COPY ./target/ZuulGateway.jar /app/target/ZuulGateway.jar

EXPOSE 20000
ENTRYPOINT ["java","-jar","/app/target/ZuulGateway.jar"]
