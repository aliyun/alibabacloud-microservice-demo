package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.server.NamedThreadFactory;
import com.alibaba.arms.mock.server.service.trouble.IServiceTroubleInjector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author aliyun
 * @date 2020/06/17
 */
@Service
@Slf4j
public class LocalService extends AbstractComponent {
    private ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new NamedThreadFactory("cpu-killer"));
    private static AtomicBoolean killCpu = new AtomicBoolean(false);

    private static Map<String, List<Object>> map = new HashMap<>();


    private static final int BYTES = 5 * 1024 * 1024;

    @Override
    public String getComponentName() {
        return "local";
    }

    @Override
    public void execute() {
        System.out.println("ok");
    }

    @Override
    public Class getImplClass() {
        return LocalService.class;
    }

    public void makeCpuBusy() {
        System.out.println("cpu");
        killCpu.set(true);
        doMakeCpuBusy();
    }

    public void cancelCpuBusy() {
        System.out.println("cancel cpu");
        killCpu.set(false);
    }

    public void fillMemory() {
        System.out.println("memory");
        doFillMemory();
    }

    public void cancelMemory() {
        System.out.println("cancel memory");
        map = new HashMap<>();
    }

    protected void doMakeCpuBusy() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            executorService.submit(() -> {
                while (killCpu.get()) {
                    Math.pow(Math.random(), Math.random());
                }
            });
        }
    }

    private void doFillMemory() {

    }
}
