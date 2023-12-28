package com.alibaba.arms.mock.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aliyun
 * @date 2021/10/29
 */
@RestController
public class HealthController {

    @RequestMapping(value = {"/", "/health"})
    public String isHealth() {
        return "I'm alive";
    }
}
