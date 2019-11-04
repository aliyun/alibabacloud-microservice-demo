package com.alibaba.edas.boot;

import org.apache.dubbo.spring.boot.env.DubboDefaultPropertiesEnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboProvider {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvider.class, args);
    }

}