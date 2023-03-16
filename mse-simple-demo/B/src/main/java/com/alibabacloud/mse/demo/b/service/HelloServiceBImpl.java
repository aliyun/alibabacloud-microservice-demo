package com.alibabacloud.mse.demo.b.service;

import com.alibabacloud.mse.demo.c.service.HelloServiceC;
import com.alibabacloud.mse.demo.common.TrafficAttribute;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service(version = "1.2.0")
public class HelloServiceBImpl implements HelloServiceB {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
    private HelloServiceC helloServiceC;

    private static final Random RANDOM = new Random();

    @Override
    public String hello(String name) {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " params:" + name + " -> " +
                helloServiceC.hello(name);
    }

    @Override
    public String slow() {
        Integer rt = TrafficAttribute.getInstance().getRt();
        Integer ration = TrafficAttribute.getInstance().getSlowRation();
        boolean isSlowRequest = RANDOM.nextInt(100) < ration ? true : false;
        if (isSlowRequest) {
            silentSleep(rt);
        }
        String slowMessage = isSlowRequest ? " slowRt:" + rt : "";
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + slowMessage;
    }

    @Override
    public String exception() {
        Integer ration = TrafficAttribute.getInstance().getExceptionRation();
        boolean isExceptionRequest = RANDOM.nextInt(100) < ration ? true : false;
        if (isExceptionRequest) {
            throw new RuntimeException("TestCircuitBreakerException");
        }
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " exceptionRation:" + ration;
    }

    private void silentSleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
