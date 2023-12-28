package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.InfrastructureCase;
import com.alibaba.arms.demo.client.utils.InvocationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/easy/local")
@Api("基础设施造故障")
@Slf4j
public class InfraController {
    @Autowired
    InfrastructureCase infrastructureCase;
    
    @ApiOperation("cpu打满")
    @RequestMapping(value = "/case/cpu", method = RequestMethod.POST)
    public String runCpuBusy() {
        return infrastructureCase.run(0,1, InvocationUtils.genCPUInvocation());
    }

    @ApiOperation("cpu打满取消")
    @RequestMapping(value = "/case/cpu/stop", method = RequestMethod.POST)
    public String stopCpu() {
        return infrastructureCase.run(0,1, InvocationUtils.genStopCPUInvocation());
    }

    @ApiOperation("内存GC")
    @RequestMapping(value = "/case/memory", method = RequestMethod.POST)
    public String runFillMemory() {
        return infrastructureCase.run(0,1, InvocationUtils.genMemoryInvocation());
    }

    @ApiOperation("取消内存GC")
    @RequestMapping(value = "/case/memory/stop", method = RequestMethod.POST)
    public String runCancelMemory() {
        return infrastructureCase.run(0,1, InvocationUtils.genMemoryStopInvocation());
    }
}
