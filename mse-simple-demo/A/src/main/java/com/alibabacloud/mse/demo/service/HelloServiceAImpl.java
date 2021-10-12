package com.alibabacloud.mse.demo.service;


import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;

import static com.alibabacloud.mse.demo.AApplication.SERVICE_TAG;

@Service(version = "1.0.0")
public class HelloServiceAImpl implements HelloServiceA{

    @Autowired
    InetUtils inetUtils;

    @Reference(application = "${dubbo.application.id}", version = "1.0.0")
    private HelloServiceB helloServiceB;

    @Override
    public String hello(String name) {
        return "A"+SERVICE_TAG+"[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello(name);
    }
}
