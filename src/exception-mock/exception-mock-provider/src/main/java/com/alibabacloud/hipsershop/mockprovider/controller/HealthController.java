package com.alibabacloud.hipsershop.mockprovider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
@Api(tags = "健康检查")
public class HealthController {

    @GetMapping("ping")
    @ApiOperation("健康检测")
    public String ping() {
        return "OK T=" + System.currentTimeMillis();
    }

}
