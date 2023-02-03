package com.alibabacloud.mse.demo.a.service;


import com.alibabacloud.mse.demo.b.service.HelloServiceB;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;

@Service(version = "1.2.0-ons-client")
public class HelloServiceAImpl implements HelloServiceA {

    @Autowired
    InetUtils inetUtils;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0-ons-client")
    private HelloServiceB helloServiceB;

    @Autowired
    String servcieTag;

    @Override
    public String hello(String name) {
        return "A" + servcieTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello(name);
    }
}
