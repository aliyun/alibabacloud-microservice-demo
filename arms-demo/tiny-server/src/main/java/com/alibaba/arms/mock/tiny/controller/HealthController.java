package com.alibaba.arms.mock.tiny.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aliyun
 * @date 2021/11/19
 */
@RestController
public class HealthController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "forward:/home";
    }
}
