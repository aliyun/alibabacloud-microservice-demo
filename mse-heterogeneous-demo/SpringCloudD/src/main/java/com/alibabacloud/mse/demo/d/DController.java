package com.alibabacloud.mse.demo.d;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

@RestController
@RequestMapping("/D")
public class DController {
    private static final Logger log = LoggerFactory.getLogger(DController.class);

    @Autowired
    String serviceTag;

    @RequestMapping(value = "/d")
    public String d(HttpServletRequest request) throws UnknownHostException {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headers =  request.getHeaderNames();
        if (headers.hasMoreElements()) {
            String headerKey = headers.nextElement();
            String value = request.getHeader(headerKey);
            sb.append(headerKey).append(":").append(value).append(", ");
        }
        log.info("/D/d request headers info: " + sb.toString());

        return "D:" + InetAddress.getLocalHost().getHostAddress() + ":" + serviceTag;
    }
}
