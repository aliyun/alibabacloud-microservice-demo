package com.alibaba.edas.xml.consumer;

import com.alibaba.edas.xml.DemoService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");

        while (true) {
            try {
                String hello = demoService.sayHello("world");
                System.out.println(hello);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
