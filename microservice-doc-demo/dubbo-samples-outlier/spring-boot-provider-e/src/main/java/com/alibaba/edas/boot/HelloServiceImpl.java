package com.alibaba.edas.boot;

import com.alibaba.dubbo.config.annotation.Service;

@Service(application = "${dubbo.application.id}",
    protocol = "${dubbo.protocol.id}",
    registry = "${dubbo.registry.id}"
)
public class HelloServiceImpl implements IHelloService {

    public String sayHello(String name) {
        if (name.equalsIgnoreCase("psw")) {
            return "Hello, " + name + " Exception ";
        }
        int i = 1;
        i /= 0;
        return "Hello, " + name + " Exception ";
    }
}