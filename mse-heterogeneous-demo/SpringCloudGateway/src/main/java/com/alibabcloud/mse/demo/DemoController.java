package com.alibabcloud.mse.demo;


import jakarta.annotation.PostConstruct;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Controller
public class DemoController {
    @Value("${background.color:white}")
    private String backgroundColor;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("backgroundColor", backgroundColor);
        return "index";
    }
}
