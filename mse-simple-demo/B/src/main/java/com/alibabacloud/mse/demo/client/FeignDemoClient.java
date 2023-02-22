package com.alibabacloud.mse.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sc-C")
public interface FeignDemoClient {

    @RequestMapping(value = "/flow", method = RequestMethod.GET)
    String flow();

}