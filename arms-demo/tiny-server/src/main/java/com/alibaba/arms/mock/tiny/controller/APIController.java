package com.alibaba.arms.mock.tiny.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author aliyun
 * @date 2021/11/26
 */
@RestController
@RequestMapping("/api/v1")
public class APIController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }

    @RequestMapping("/user/{userId}/info")
    public String getUserInfo(@PathVariable("userId") String userId) {
        return "hello " + userId;
    }


    @RequestMapping("/order/{orderId}/info")
    public String getOrderInfo(@PathVariable("orderId") String orderId) {
        return "hello " + orderId;
    }

    @RequestMapping("/any/**/info")
    public String anyPath(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURL().toString();
    }
}
