package com.alibabacloud.mse.demo.common;

import java.util.concurrent.atomic.AtomicInteger;

public class TrafficAttribute {
    private static TrafficAttribute instance = new TrafficAttribute();
    private TrafficAttribute() {};
    public static TrafficAttribute getInstance() {
        return instance;
    }

    public static AtomicInteger REQUEST_SLOW_RT = new AtomicInteger(5);
    public static AtomicInteger REQUEST_EXCEPTION_RATION = new AtomicInteger(0);
    public static AtomicInteger REQUEST_SLOW_RATION = new AtomicInteger(100);

    public Integer getRt() {
        return REQUEST_SLOW_RT.get();
    }

    public void setRt(Integer rt) {
        REQUEST_SLOW_RT.set(rt);
    }

    public Integer getSlowRation() {
        return REQUEST_SLOW_RATION.get();
    }

    public void setSlowRation(Integer slowRation) {
        REQUEST_SLOW_RATION.set(slowRation);
    }

    public Integer getExceptionRation() {
        return REQUEST_EXCEPTION_RATION.get();
    }

    public void setExceptionRation(Integer exceptionRation) {
        REQUEST_EXCEPTION_RATION.set(exceptionRation);
    }
}