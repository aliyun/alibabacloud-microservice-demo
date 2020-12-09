package com.alibaba.edas.boot;

import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0", group = "DUBBO")
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String name) {
        return "hello" + name;

    }
}
