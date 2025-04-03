FROM registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/maven:3-eclipse-temurin-17 as build

COPY <<EOF /root/.m2/settings.xml
<?xml version="1.0"?>
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


FROM registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y \
    vim unzip netcat-openbsd telnet tcpdump iproute2 lsof ngrep \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/SpringCloudD.jar /app/target/SpringCloudD.jar

EXPOSE 20004
ENTRYPOINT ["java","-jar","/app/target/SpringCloudD.jar"]
