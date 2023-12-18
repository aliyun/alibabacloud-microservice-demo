package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.RocketmqService;
import com.alibaba.arms.demo.client.utils.InvocationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.rocketmq.client.apis.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RestController
@RequestMapping("/easy/mq")
@Api("mq测试案例")
public class MqCaseController {
    @Autowired
    RocketmqService rocketmqService;
    
    @ApiOperation("启动")
    @RequestMapping(value = "/case/start", method = RequestMethod.POST)
    public String runMq(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                   Integer durationSeconds,
                           @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                   Integer parallel
    ) {
        try {
            rocketmqService.open();
            rocketmqService.sendMessage();
        } catch (Exception e) {
        }
        return "success";
    }

    @ApiOperation("终止")
    @RequestMapping(value = "/case/stop", method = RequestMethod.POST)
    public String stopMq() {
        try {
            rocketmqService.off();
        } catch (Exception e) {
        }
        return "success";
    }
}
