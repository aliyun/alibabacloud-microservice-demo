package com.alibabacloud.hipstershop.productservice.controller;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.alibabacloud.hipstershop.productservice.utils.CommonUtil.getLocalIp;

@RestController
public class SetExceptionByIpController {

    @Value("${spring.application.name}")
    private String appName;

    private static Integer memoryOut;

    @RequestMapping(value = "/setExceptionByIp", method = RequestMethod.GET)
    public String setExceptionByIp(@RequestParam("ip") String ip) {
        String localIp = getLocalIp();
        if (localIp.equals(ip)) {
            throw new RuntimeException();
        }
        return localIp;
    }

    @RequestMapping(value = "/setMemoryOutByIp", method = RequestMethod.GET)
    @Async
    public void setMemoryOutByIp(@RequestParam("ip") String ip, @RequestParam("serviceName") String serviceName, @RequestParam("type") Integer type) {
        if(serviceName.equals(appName)) {
            String localIp = getLocalIp();
            if (localIp.equals(ip)) {
                memoryOut = type;
                Vector<Object> v = new Vector<>();
                while (memoryOut == 1) {
                    v.add(new Object());
                }
                v = null;
            }
        }
    }
}
