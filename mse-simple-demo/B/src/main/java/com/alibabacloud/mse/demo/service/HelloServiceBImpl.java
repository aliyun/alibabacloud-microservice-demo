package com.alibabacloud.mse.demo.service;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;

@Service(version = "1.1.0")
public class HelloServiceBImpl implements HelloServiceB {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Reference(application = "${dubbo.application.id}", version = "1.1.0")
    private HelloServiceC helloServiceC;

    @Override
    public String hello(String name) {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.hello(name);
    }

}
