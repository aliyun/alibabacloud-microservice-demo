package com.alibabacloud.mse.demo.a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@RestController
@RequestMapping("/A")
public class AController {
    private static final Logger log = LoggerFactory.getLogger(AController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @RequestMapping(value = "/d")
    public String d(@RequestHeader Map<String, String> headers) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
        }
        log.info("/A/d request headers info: " + sb);
        String resp = loadBalancedRestTemplate.getForObject("http://spring-cloud-d/D/d", String.class);
        return "A:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag + " - " + resp;
    }

    @RequestMapping(value = "/a")
    public String a(@RequestParam(name = "call_type", required = false) String callType,
                    @RequestHeader Map<String, String> headers) throws UnknownHostException {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
        }
        log.info("/A/a request headers info: " + sb);
        if (callType == null || !callType.equalsIgnoreCase("golang")) {
            return callSpringBoot();
        }
        return callGolang();
    }

    private String callSpringBoot() throws UnknownHostException {
        String resp = restTemplate.getForObject("http://spring-boot-b:20002/B/b", String.class);
        return "A:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag + " -(java)- " + resp;
    }

    private String callGolang() throws UnknownHostException {
        String resp = restTemplate.getForObject("http://gin-c:20003/C/c", String.class);
        return "A:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag + " -(golang)- " + resp;
    }
}
