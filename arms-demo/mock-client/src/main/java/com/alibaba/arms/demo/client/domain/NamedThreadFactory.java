package com.alibaba.arms.demo.client.domain;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aliyun
 * @date 2021/07/20
 */
public class NamedThreadFactory implements ThreadFactory {

    private static AtomicInteger tag = new AtomicInteger(0);

    private String name;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(name + ":" + tag.getAndIncrement());
        return thread;
    }
}
