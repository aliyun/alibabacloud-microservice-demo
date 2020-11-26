package com.alibabacloud.hipstershop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/gateway")
public class GatewayTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayTestController.class);

    private Random random = new Random();

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String name(
            @RequestParam(value = "name") String name){

        int timeout = random.nextInt(100);
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            LOGGER.error("sleep", e);
        }

        return "hello " + name;
    }

}
