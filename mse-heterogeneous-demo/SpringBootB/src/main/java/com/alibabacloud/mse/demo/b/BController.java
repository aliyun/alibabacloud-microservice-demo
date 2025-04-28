package com.alibabacloud.mse.demo.b;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/B")
public class BController {
    private static final Logger log = LoggerFactory.getLogger(BController.class);

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private String serviceTag;

    private WebClient webClient;

    @RequestMapping(value = "/b")
    public String b(@RequestHeader Map<String, String> headers) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
        }
        log.info("/B/b request headers info: " + sb);

        if (webClient == null) {
            webClient = webClientBuilder.baseUrl("http://spring-cloud-d:20004").build();
        }
        Mono<String> mono = webClient.get().uri("/D/d").retrieve().bodyToMono(String.class);
        String resp = mono.block();
        return "B:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag + " - " + resp;
    }
}
