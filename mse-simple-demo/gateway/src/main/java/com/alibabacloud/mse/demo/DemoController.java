package com.alibabacloud.mse.demo;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Controller
public class DemoController {


    private static final ScheduledExecutorService FLOW_EXECUTOR = Executors.newScheduledThreadPool(1,
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("dubbo.outlier.refresh-" + thread.getId());
                    return thread;
                }
            });

    @GetMapping("/demo")
    public String index(Model model) {
        return "index";
    }

    @PostConstruct
    private void flow() {
        FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                    HttpGet httpGet = new HttpGet("http://localhost:20000/A/a");
                    httpClient.execute(httpGet);

                } catch (Exception ignore) {
                }
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }
}
