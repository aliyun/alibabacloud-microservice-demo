package com.alibabacloud.mse.demo.c;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
    public String c(HttpServletRequest request) {
        if (throwException) {
            throw new RuntimeException();
        }
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

    @GetMapping("/c-zone")
    public String cZone(HttpServletRequest request) {
        if (throwException) {
            throw new RuntimeException();
        }
        return "C" + serviceTag + "[" + currentZone + "]";
    }

    @GetMapping("/spring_boot")
    public String spring_boot(HttpServletRequest request) {
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }


    @GetMapping("/flow")
    public String flow(HttpServletRequest request) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime;
    }

    @GetMapping("/params/{hot}")
    public String params(@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime + " params:" + hot;
    }

    @GetMapping("/isolate")
    public String isolate(HttpServletRequest request) throws ExecutionException, InterruptedException {
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
