package com.alibaba.arms.mock.server.service.trouble;

import com.alibaba.arms.mock.server.NamedThreadFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@Service
public class JVMCpuTrouble extends AbstractTrouble {

    private ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            new NamedThreadFactory("cpu-killer"));

    @Override
    public TroubleEnum getTroubleType() {
        return TroubleEnum.JVM_CPU;
    }

    @Override
    public List<String> getAffectedResource() {
        return this.working.get() ? Arrays.asList("jvm-cpu") : Collections.emptyList();
    }

    @Override
    protected void doCancel() {

    }

    @Override
    protected void doMake(Map<String, String> params) {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    while (working.get()) {
                        Math.pow(Math.random(), Math.random());
                    }
                }
            });
        }
    }
}
