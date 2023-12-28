# syntax=docker/dockerfile:1.4
FROM maven:3-eclipse-temurin-8 as build

COPY <<EOF /root/.m2/settings.xml
<?xml version=\"1.0\"?>
<settings>
    <mirrors>
        <mirror>
            <id>alimaven</id>
            <name>aliyun maven</name>
            <url>https://maven.aliyun.com/nexus/content/groups/public/</url>
            <mirrorOf>central</mirrorOf>
        </mirror>
    </mirrors>
</settings>
EOF

WORKDIR /app

COPY ./ ./

RUN --mount=type=cache,target=/root/.m2/repository/ \
  mvn clean package --batch-mode

FROM ibm-semeru-runtimes:open-8-jdk

# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

COPY --from=build /app/target/B.jar /app/target/B.jar
#COPY ./target/B.jar /app/target/B.jar

EXPOSE 20002
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar /app/target/B.jar"]
