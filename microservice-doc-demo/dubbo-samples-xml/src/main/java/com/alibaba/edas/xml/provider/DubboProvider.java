package com.alibaba.edas.xml.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class DubboProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-demo-provider.xml");
        context.start();

        new CountDownLatch(1).await();
    }
}
