package com.alibaba.arms.mock.server.domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 存活期到后自动将值更新为初始值
 *
 * @author aliyun
 * @date 2022/02/08
 */

public class AutoResetObjectWrapper<T> {


    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();


    private final AtomicReference<T> reference;

    private final T init;

    private final int aliveSeconds;

    /**
     * @param init         初始值
     * @param aliveSeconds 存活时间
     */
    public AutoResetObjectWrapper(T init, int aliveSeconds) {
        this.reference = new AtomicReference<>(init);
        this.init = init;
        this.aliveSeconds = aliveSeconds;
    }

    public AutoResetObjectWrapper(T init) {
        this(init, 1200);
    }

    public T update(T currentVal) {
        T oldOne = this.reference.getAndSet(currentVal);
        SCHEDULED_EXECUTOR_SERVICE.schedule(new Runnable() {
            @Override
            public void run() {
                reference.set(init);
            }
        }, this.aliveSeconds, TimeUnit.SECONDS);
        return this.get();
    }

    public T get() {
        return reference.get();
    }

}
