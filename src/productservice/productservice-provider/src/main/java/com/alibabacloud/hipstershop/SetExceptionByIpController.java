package com.alibabacloud.hipstershop;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetExceptionByIpController {

    @RequestMapping(value = "/setExceptionByIp", method = RequestMethod.GET)
    public String setExceptionByIp(@RequestParam("ip")  String ip) {
        String localIp = getLocalIp();
        if (localIp.equals(ip)) {
            throw new RuntimeException();
        }

        return localIp;
    }

    private String getLocalIp() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            if (inetAddress != null) {
                return inetAddress.getHostAddress();//获得本机Ip;
            }
        } catch (UnknownHostException e) {
        }
        return null;
    }
}
