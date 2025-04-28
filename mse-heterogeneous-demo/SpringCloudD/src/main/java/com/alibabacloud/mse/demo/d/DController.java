package com.alibabacloud.mse.demo.d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@RestController
@RequestMapping("/D")
public class DController {
    private static final Logger log = LoggerFactory.getLogger(DController.class);

    @Autowired
    String serviceTag;

    @RequestMapping(value = "/d")
    public String d(@RequestHeader Map<String, String> headers) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(", ");
        }
        log.info("/D/d request headers info: " + sb);

        return "D:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag;
    }
}
