package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.HttpCase;
import com.alibaba.arms.demo.client.utils.InvocationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@RestController
@RequestMapping("/easy/http")
@Api("Http测试案例")
public class HttpCaseController {
    public static AtomicLong httpSlowInjectTime = new AtomicLong(0);
    public static AtomicLong httpErrorInjectTime = new AtomicLong(0);
    
    @Autowired
    private HttpCase httpCase;
    @PostConstruct
    public void init() {
        try {
            this.httpCase.run(0,5,  InvocationUtils.genHttpInvocation());
//            this.httpCase.run(0,3,  InvocationUtils.genHttpInvocation2());
//            this.httpCase.run(0,3,  InvocationUtils.genHttpInvocation3());
        } catch (Exception e) {
        }
    }
    
    @ApiOperation("启动")
    @RequestMapping(value = "/case/start", method = RequestMethod.POST)
    public String runHttp(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                  Integer durationSeconds,
                          @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                  Integer parallel) {
        return httpCase.run(durationSeconds, null == parallel ? 1 : parallel, InvocationUtils.genHttpInvocation());
    }


    @ApiOperation("停止流量注入")
    @RequestMapping(value = "/case/stop", method = RequestMethod.POST)
    public String runDubbo() {
        return httpCase.stop();
    }

    @ApiOperation("注入Http错误")
    @RequestMapping(value = "/inject_http_error", method = RequestMethod.POST)
    public String injectHttpError() {
        httpErrorInjectTime.set(System.currentTimeMillis());
        return httpCase.injectError();
    }

    @ApiOperation("终止注入Http错误")
    @RequestMapping(value = "/stop_inject_http_error", method = RequestMethod.POST)
    public String stopInjectHttpError() {
        httpErrorInjectTime.set(0);
        return httpCase.stopInjectError();
    }


    @ApiOperation("注入Http慢")
    @RequestMapping(value = "/inject_http_slow", method = RequestMethod.POST)
    public String injectHttpSlow(
            @ApiParam(value = "slowSeconds", defaultValue = "3") @RequestParam(required = false, name = "slowSeconds")
                    Double slowSeconds
    ) {
        httpSlowInjectTime.set(System.currentTimeMillis());
        return httpCase.injectSlow(slowSeconds);
    }

    @ApiOperation("终止注入Http慢")
    @RequestMapping(value = "/stop_inject_http_slow", method = RequestMethod.POST)
    public String stopInjectHttpSlow(
    ) {
        httpSlowInjectTime.set(0);
        return httpCase.stopInjectSlow();
    }


}
