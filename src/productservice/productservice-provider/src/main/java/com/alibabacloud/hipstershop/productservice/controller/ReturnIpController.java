package com.alibabacloud.hipstershop.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yizhan.xj
 */

@RestController
public class ReturnIpController {

    @Autowired
    private Registration registration;

    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getIp(@RequestParam("name") String name, @RequestParam("age") int age) {
        return registration.getHost();
    }
}
