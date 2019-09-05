package com.alibaba.edas.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class DubboProvider {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DubboProvider.class, args);
    }
}