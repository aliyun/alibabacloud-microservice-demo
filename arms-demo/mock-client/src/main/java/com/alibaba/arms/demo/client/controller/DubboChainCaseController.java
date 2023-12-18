package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.DubboChainCase;
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
 * @author aliyun
 * @date 2021/07/20
 */
@RestController
@RequestMapping("/easy/dubbo-chain")
@Api("Dubbo测试案例")
@Slf4j
public class DubboChainCaseController {

    @Autowired
    private DubboChainCase dubboChainCase;


    @ApiOperation("启动")
    @RequestMapping(value = "/case/start", method = RequestMethod.POST)
    public String runDubbo(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                   Integer durationSeconds,
                           @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                   Integer parallel) {
        return dubboChainCase.run(durationSeconds, null == parallel ? 1 : parallel);
    }

    @ApiOperation("停止流量注入")
    @RequestMapping(value = "/case/stop", method = RequestMethod.POST)
    public String runDubbo() {
        return dubboChainCase.stop();
    }

    @ApiOperation("注入Dubbo错误")
    @RequestMapping(value = "/inject_dubbo_error", method = RequestMethod.POST)
    public String injectDubboError() {
        return dubboChainCase.injectError();
    }

    @ApiOperation("终止注入Dubbo错误")
    @RequestMapping(value = "/stop_inject_dubbo_error", method = RequestMethod.POST)
    public String stopInjectDubboError() {
        return dubboChainCase.stopInjectError();
    }


    @ApiOperation("注入Dubbo慢")
    @RequestMapping(value = "/inject_dubbo_slow", method = RequestMethod.POST)
    public String injectDubboSlow(
            @ApiParam(value = "slowSeconds", defaultValue = "3") @RequestParam(required = false, name = "slowSeconds")
                    Double slowSeconds
    ) {
        return dubboChainCase.injectSlow(slowSeconds);
    }

    @ApiOperation("终止注入Dubbo慢")
    @RequestMapping(value = "/stop_inject_dubbo_slow", method = RequestMethod.POST)
    public String stopInjectDubboSlow(
    ) {
        return dubboChainCase.stopInjectSlow();
    }
}
