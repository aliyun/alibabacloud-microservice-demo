package com.alibabacloud.mse.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

@Controller
public class DemoController {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Value("${demo.qps:100}")
    private int qps;

    @Value("${enable.rpc.invoke:true}")
    private boolean enableRpcInvoke;

    @Value("${background.color:white}")
    private String backgroundColor;

    @Value("${enable.sql:false}")
    private boolean enableSql;

    @Value("${enable.auto:true}")
    private boolean enableAuto;

    @Value("${enable.sentinel.demo.flow:true}")
    private boolean enableSentinelFlow;

    @Value("${enable.gray:false}")
    private boolean enableGray;

    private static Random random = new Random();

    private static final ScheduledExecutorService FLOW_EXECUTOR = Executors.newScheduledThreadPool(16,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("dubbo.outlier.refresh-" + thread.getId());
                    return thread;
                }
            });

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("backgroundColor", backgroundColor);
        return "index";
    }

    @GetMapping("/spring_boot")
    @ResponseBody
    public String spring_boot(HttpServletRequest request) {
        String result = restTemplate.getForObject("http://sc-a:20001/spring_boot", String.class);
        return result;
    }

    @PostConstruct
    private void flow() {

    }
}
