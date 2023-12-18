package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.BaseTestCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author aliyun
 * @date 2022/02/25
 */
@RestController
@RequestMapping("/easy/one")
@Api("一键恢复")
@Slf4j
public class ResetController {

    @Autowired
    private List<BaseTestCase> testCases;


    @ApiOperation("启动所有流量")
    @RequestMapping(value = "/start_all_cases", method = RequestMethod.POST)
    public String startAllCases() {
        if (null != testCases) {
            testCases.forEach(e -> {
                if (!e.getCaseName().equalsIgnoreCase("bad_sql")) {
                    e.run(0, 1);
                }
            });
        }
        return "success";
    }


    @ApiOperation("终止所有流量")
    @RequestMapping(value = "/stop_all_cases", method = RequestMethod.POST)
    public String stopAllCases() {
        if (null != testCases) {
            testCases.forEach(e -> {
                e.stop();
            });
        }
        return "success";
    }

    @ApiOperation("终止所有故障注入")
    @RequestMapping(value = "/stop_all_trouble", method = RequestMethod.POST)
    public String stopAllTroubles(
    ) {
        if (null != testCases) {
            testCases.forEach(e -> {
                e.stopInjectSlow();
                e.stopInjectError();
            });
        }
        return "success";
    }

}
