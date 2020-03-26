package com.alibaba.edas.boot;

import com.alibaba.dubbo.config.annotation.Reference;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;

@RestController
public class DemoConsumerController {

    @Reference(application = "${dubbo.application.id}", registry = "edas")
    private IHelloService demoService;

    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        return demoService.sayHello(name);
    }

    @PostConstruct
    public void init() {
        final ScheduledExecutorService executorService = Executors
            .newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
//                    System.out.println(demoService.sayHello("12345"));
                } catch (Exception e) {

                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

}
