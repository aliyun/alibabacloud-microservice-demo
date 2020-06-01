package com.alibabacloud.hipstershop.common;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.EvictingQueue;

/**
 * @author yizhan.xj
 */
public class CommonUtil {

    public static final AtomicBoolean ROUTER_BEGIN = new AtomicBoolean(false);

    public static final AtomicBoolean INVOKER_ENABLE = new AtomicBoolean(false);

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    public static final Object DUBBO_LOCK = new Object();
    public static final Object SPRING_CLOUD_LOCK = new Object();

    public static Queue<String> DUBBO_RESULT_QUEUE = EvictingQueue.create(1000);

    public static Queue<String> SPRING_CLOUD_RESULT_QUEUE = EvictingQueue.create(1000);

    public static String dubbo_name = "xiaoming";
    public static String spring_cloud_name = "xiaoming";
    public static int dubbo_age = 0;
    public static int spring_cloud_age = 0;

}
