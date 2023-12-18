package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.ComponentService;
import com.alibaba.arms.mock.server.service.LocalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components/api/v1")
@Api("local")
public class LocalController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private LocalService localService;

    @RequestMapping(value = "/local/success", method = RequestMethod.POST)
    public String success(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        localService.execute();
        return componentService.execute(children, level);
    }

    @RequestMapping(value = "/local/cpu", method = RequestMethod.POST)
    public String cpu(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        localService.makeCpuBusy();
        return componentService.execute(children, level);
    }

    @RequestMapping(value = "/local/stopCpu", method = RequestMethod.POST)
    public String cpuStop(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        localService.cancelCpuBusy();
        return componentService.execute(children, level);
    }

    @RequestMapping(value = "/local/memory", method = RequestMethod.POST)
    public String memory(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        localService.fillMemory();
        return componentService.execute(children, level);
    }

    @RequestMapping(value = "/local/stopMemory", method = RequestMethod.POST)
    public String memoryStop(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        localService.cancelMemory();
        return componentService.execute(children, level);
    }
}


