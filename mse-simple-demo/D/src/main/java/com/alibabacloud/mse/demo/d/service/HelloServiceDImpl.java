package com.alibabacloud.mse.demo.d.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;

@Slf4j
@DubboService(version = "1.2.0")
@RequiredArgsConstructor
public class HelloServiceDImpl implements com.alibabacloud.mse.demo.c.service.HelloServiceD {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    @Override
    public String hello(String name) {

        return name;
    }

    @Override
    public String world(String name) {
        return  "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "] -> " + name;
    }

}
