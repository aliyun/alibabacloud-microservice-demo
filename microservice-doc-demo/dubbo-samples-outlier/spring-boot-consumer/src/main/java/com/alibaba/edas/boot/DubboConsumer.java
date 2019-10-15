package com.alibaba.edas.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboConsumer {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumer.class, args);
    }
//-javaagent:/Users/panshengwei/Documents/ms/arms-java-agent/agent/target/arms-agent-1.7.0-SNAPSHOT/arms-bootstrap-1.7.0-SNAPSHOT.jar -Darms.licenseKey=aokcdqn3ly@03cd8c21942bee9 -Darms.appName=dubboDemo -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005
}
