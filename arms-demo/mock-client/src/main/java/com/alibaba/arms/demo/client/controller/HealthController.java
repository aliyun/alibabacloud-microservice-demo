package com.alibaba.arms.demo.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aliyun
 * @date 2021/10/29
 */
@Controller
public class HealthController {

    @RequestMapping(value = {"/", "/health"})
    public String isHealth() {
        return "forward:/home/";
    }
}
