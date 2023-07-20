package com.alibabacloud.mse.demo;

import com.alibabacloud.mse.demo.service.HelloServiceB;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.alibabacloud.mse.demo.ConsumerApplication.SERVICE_TAG;

@RestController
public class AController {


    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    @Reference(application = "${dubbo.application.id}", version = "1.0.0")
    private HelloServiceB helloServiceB;

    @Value("${name:123}")
    private String name;


    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/a")
    public String a(HttpServletRequest request) {
        return "A"+SERVICE_TAG+"[" + request.getLocalAddr() + "]" + " -> " +
                restTemplate.getForObject("http://provider/b", String.class);
    }

    @GetMapping("/dubbo")
    public String dubbo(HttpServletRequest request) {
        return "A"+SERVICE_TAG+"[" + request.getLocalAddr() + "]" + " -> " +
                helloServiceB.hello("A");
    }

    @PostConstruct
    private void printConfig() {
        EXECUTOR_SERVICE.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println(name);
            }
        },0,2, TimeUnit.SECONDS);
    }
}
