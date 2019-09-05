package com.alibaba.edas.boot;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class IHelloServiceImpl implements IHelloService {
    public String sayHello(String name) {
        return "Hello, " + name + " (from Dubbo with Spring Boot)";
    }
}