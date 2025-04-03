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
    public String b(HttpServletRequest request) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headers =  request.getHeaderNames();
        if (headers.hasMoreElements()) {
            String headerKey = headers.nextElement();
            String value = request.getHeader(headerKey);
            sb.append(headerKey).append(":").append(value).append(", ");
        }

        log.info("/B/b request headers info: " + sb.toString());

        if (webClient == null) {
            webClient = webClientBuilder.baseUrl("http://spring-cloud-d:20004").build();
        }
        Mono<String> mono = webClient.get().uri("/D/d").retrieve().bodyToMono(String.class);
        String resp = mono.block();
        return "B:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag + " - " + resp;
    }
}
