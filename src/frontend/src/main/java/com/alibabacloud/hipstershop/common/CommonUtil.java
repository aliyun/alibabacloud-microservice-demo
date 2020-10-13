package com.alibabacloud.hipstershop.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.EvictingQueue;

/**
 * @author yizhan.xj
 */
public class CommonUtil {

    public static final int PRODUCT_NUM = 6;

    public static final AtomicBoolean ROUTER_BEGIN = new AtomicBoolean(false);

    public static final AtomicBoolean INVOKER_ENABLE = new AtomicBoolean(false);

    public static final AtomicBoolean AUTH_BEGIN = new AtomicBoolean(false);

    public static final AtomicBoolean AUTH_ENABLE = new AtomicBoolean(false);

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static final Object DUBBO_LOCK = new Object();
    public static final Object TAG_LOCK = new Object();
    public static final Object SPRING_CLOUD_LOCK = new Object();
    public static final Object DUBBO_AUTH_LOCK = new Object();
    public static final Object[] PRODUCT_LOCK = new Object[PRODUCT_NUM];

    public static Queue<String> DUBBO_RESULT_QUEUE = EvictingQueue.create(1000);
    public static Queue<String> PERCENT_RESULT_QUEUE = EvictingQueue.create(1000);

    public static Queue<String> SPRING_CLOUD_RESULT_QUEUE = EvictingQueue.create(1000);

    public static AtomicLong DUBBO_INVOKER_TIMES = new AtomicLong(0);
    public static AtomicLong SPRING_CLOUD_INVOKER_TIMES = new AtomicLong(0);
    public static AtomicLong DUBBO_ERROR_TIMES = new AtomicLong(0);
    public static AtomicLong SPRING_CLOUD_ERROR_TIMES = new AtomicLong(0);

    public static Queue<String> DUBBO_AUTH_RESULT_QUEUE = EvictingQueue.create(10);
    public static Queue<String>[] SPRING_CLOUD_AUTH_RESULT_QUEUE = new EvictingQueue[PRODUCT_NUM];

    // EvictingQueue声明成数组以后使用上有一点问题，只好对每个产品分别声明
    public static Queue<String> PRODUCT_QUEUE0 = EvictingQueue.create(10);
    public static Queue<String> PRODUCT_QUEUE1 = EvictingQueue.create(10);
    public static Queue<String> PRODUCT_QUEUE2 = EvictingQueue.create(10);
    public static Queue<String> PRODUCT_QUEUE3 = EvictingQueue.create(10);
    public static Queue<String> PRODUCT_QUEUE4 = EvictingQueue.create(10);
    public static Queue<String> PRODUCT_QUEUE5 = EvictingQueue.create(10);


    public static String dubbo_name = "xiaoming";
    public static String spring_cloud_name = "xiaoming";

    public static String[] products = {"OLJCESPC7Z", "66VCHSJNUP", "1YMWWN1N4O", "L9ECAV7KIM", "2ZYFJ3GM2N", "0PUK6V6EV0"};

    public static int dubbo_age = 0;
    public static int spring_cloud_age = 0;

    public static String getLocalIp() throws UnknownHostException {
        InetAddress address = Inet4Address.getLocalHost();
        return address.getHostAddress();
    }

}
