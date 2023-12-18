package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.DubboCase;
import com.alibaba.arms.demo.client.service.DubboIgnoreExceptionCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 *
 * dubbo内部出现异常，但是整体错误数无变化
 *
 * @author aliyun
 * @date 2021/07/20
 */
@RestController
@RequestMapping("/easy/dubbo-ignore-exception")
@Api("Dubbo测试案例")
@Slf4j
public class DubboIgnoreExceptionCaseController {

    @Autowired
    private DubboIgnoreExceptionCase dubboCase;

    @PostConstruct
    public void init() {
        try {
            this.dubboCase.run(0, 1);
        } catch (Exception e) {
            log.error("start dubbo case failed", e);
        }
    }

    @ApiOperation("启动")
    @RequestMapping(value = "/case/start", method = RequestMethod.POST)
    public String runDubbo(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                   Integer durationSeconds,
                           @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                   Integer parallel) {
        return dubboCase.run(durationSeconds, null == parallel ? 1 : parallel);
    }

    @ApiOperation("停止流量注入")
    @RequestMapping(value = "/case/stop", method = RequestMethod.POST)
    public String runDubbo() {
        return dubboCase.stop();
    }

    @ApiOperation("注入Dubbo错误")
    @RequestMapping(value = "/inject_dubbo_error", method = RequestMethod.POST)
    public String injectDubboError() {
        return dubboCase.injectError();
    }

    @ApiOperation("终止注入Dubbo错误")
    @RequestMapping(value = "/stop_inject_dubbo_error", method = RequestMethod.POST)
    public String stopInjectDubboError() {
        return dubboCase.stopInjectError();
    }


    @ApiOperation("注入Dubbo慢")
    @RequestMapping(value = "/inject_dubbo_slow", method = RequestMethod.POST)
    public String injectDubboSlow(
            @ApiParam(value = "slowSeconds", defaultValue = "3") @RequestParam(required = false, name = "slowSeconds")
                    Double slowSeconds
    ) {
        return dubboCase.injectSlow(slowSeconds);
    }

    @ApiOperation("终止注入Dubbo慢")
    @RequestMapping(value = "/stop_inject_dubbo_slow", method = RequestMethod.POST)
    public String stopInjectDubboSlow(
    ) {
        return dubboCase.stopInjectSlow();
    }
}
