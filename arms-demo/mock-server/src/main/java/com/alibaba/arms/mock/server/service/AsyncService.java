package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.api.Invocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author aliyun
 * @date 2021/06/22
 */
@Component
@Slf4j
public class AsyncService extends AbstractComponent {
    private static final ExecutorService es = Executors.newFixedThreadPool(5);
    @Autowired
    private ComponentService componentService;

    @Override
    public String getComponentName() {
        return "async";
    }

    @Override
    public void execute() {
        Future<?> future = es.submit(new Runnable() {
            @Override
            public void run() {
                Invocation invocation = new Invocation();
                invocation.setChildren(null);
                invocation.setComponent("redis");
                invocation.setMethod("success");
                componentService.randomRemoteCall(invocation, 0);
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            log.error("got error", e);
        }
    }

    @Override
    public Class getImplClass() {
        return AsyncService.class;
    }
}
