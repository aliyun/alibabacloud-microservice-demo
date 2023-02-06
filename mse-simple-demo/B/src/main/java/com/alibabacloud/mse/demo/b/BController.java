package com.alibabacloud.mse.demo.b;

import com.alibabacloud.mse.demo.c.service.HelloServiceC;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
class BController {

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
    private HelloServiceC helloServiceC;

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

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


    @GetMapping("/flow")
    public String flow(HttpServletRequest request) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);

        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + sleepTime;
    }

    @GetMapping("/params/{hot}")
    public String params(HttpServletRequest request,@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        helloServiceC.hello(hot);
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + sleepTime+":"+hot;
    }

    @GetMapping("/isolate")
    public String isolate(HttpServletRequest request) throws ExecutionException, InterruptedException {
        long sleepTime = 20 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + sleepTime;
    }


    @GetMapping("/flow-c")
    public String flow_c(HttpServletRequest request) throws ExecutionException, InterruptedException {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.hello("B");
    }

    @GetMapping("/params-c/{hot}")
    public String params_c(HttpServletRequest request,@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.hello(hot);
    }

    @GetMapping("/isolate-c")
    public String isolate_c(HttpServletRequest request) throws ExecutionException, InterruptedException {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.hello("B");
    }

    @GetMapping("/rpc-c")
    public String rpc_c(HttpServletRequest request) throws ExecutionException, InterruptedException {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.world("B");
    }

    @GetMapping("/b")
    public String b(HttpServletRequest request) {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                loadBalancedRestTemplate.getForObject("http://sc-C/c", String.class);
    }

    @GetMapping("/b-zone")
    public String bZone(HttpServletRequest request) {
        return "B" + serviceTag + "[" + currentZone + "]" + " -> " +
                loadBalancedRestTemplate.getForObject("http://sc-C/c-zone", String.class);
    }

    @GetMapping("/spring_boot")
    public String spring_boot(HttpServletRequest request) {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                restTemplate.getForObject("http://sc-c:20003/spring_boot", String.class);
    }


    private void silentSleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

}
