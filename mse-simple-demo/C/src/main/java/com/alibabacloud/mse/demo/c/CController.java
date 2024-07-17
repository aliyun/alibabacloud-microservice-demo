package com.alibabacloud.mse.demo.c;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
class CController {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    private String currentZone;

    private static final Random RANDOM = new Random();

    @PostConstruct
    private void init() {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)
                    .setConnectTimeout(1000)
                    .setSocketTimeout(1000)
                    .build();
            HttpGet req = new HttpGet("http://100.100.100.200/latest/meta-data/zone-id");
            req.setConfig(requestConfig);
            HttpResponse response = client.execute(req);
            currentZone = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            currentZone = e.getMessage();
        }

    }

    @GetMapping("/c")
    public String c() {
        if (throwException) {
            throw new RuntimeException();
        }
        try (Entry entry1 = SphU.entry("HelloWorld-c-1", EntryType.IN)) {
            // 具体的业务逻辑
            try {
                return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
            } catch (Throwable e) {
                // 标记此次资源调用失败
                entry1.setError(e);
                throw e;
            }
        } catch (BlockException e) {
            // 处理限流发生后的逻辑
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/c-zone")
    public String cZone() {
        if (throwException) {
            throw new RuntimeException();
        }
        try (Entry entry2 = SphU.entry("HelloWorld-c-2", EntryType.IN)) {
            try {
                log.debug("Hello Sentinel!2");
                return "C" + serviceTag + "[" + currentZone + "]";
            } catch (Throwable e) {
                entry2.setError(e);
                throw e;
            }
        } catch (BlockException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/spring_boot")
    @SentinelResource("sc_spring_boot")
    public String spring_boot() {
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

    @GetMapping("/flow")
    public String flow() {
        try (Entry entry1 = SphU.entry("HelloWorld-c-flow-1", EntryType.IN)) {
            log.debug("Hello Sentinel!1");
            try (Entry entry2 = SphU.entry("HelloWorld-c-flow-2", EntryType.IN)) {
                log.debug("Hello Sentinel!2");
                try {
                    long sleepTime = 5 + RANDOM.nextInt(5);
                    silentSleep(sleepTime);
                    return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime;
                } catch (Throwable throwable) {
                    entry2.setError(throwable);
                    entry1.setError(throwable);
                    throw throwable;
                }
            } catch (BlockException e) {
                throw new RuntimeException(e);
            }
        } catch (BlockException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/params/{hot}")
    public String params(@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime + " params:" + hot;
    }

    @GetMapping("/isolate")
    public String isolate() throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime;
    }

    private void silentSleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
