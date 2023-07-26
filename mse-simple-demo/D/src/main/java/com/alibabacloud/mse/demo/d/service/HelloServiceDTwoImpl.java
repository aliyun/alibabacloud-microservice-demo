package com.alibabacloud.mse.demo.d.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@DubboService(version = "1.2.0")
@RequiredArgsConstructor
public class HelloServiceDTwoImpl implements com.alibabacloud.mse.demo.c.service.HelloServiceDTwo {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    @Override
    public String hello2(String name) {

        return name;
    }

    @Override
    public String world2(String name) {
        return  "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "] -> " + name;
    }

}
