package com.alibabacloud.mse.demo.d;

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
class DController {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    private String currentZone;

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

    @GetMapping("/d")
    public String d(HttpServletRequest request) {
        if (throwException) {
            throw new RuntimeException();
        }
        return "D" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

    @GetMapping("/d-zone")
    public String dZone(HttpServletRequest request) {
        if (throwException) {
            throw new RuntimeException();
        }
        return "D" + serviceTag + "[" + currentZone + "]";
    }

}
