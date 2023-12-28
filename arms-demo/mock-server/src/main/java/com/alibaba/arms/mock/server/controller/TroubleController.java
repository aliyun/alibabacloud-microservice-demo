package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.dto.BigKeyTroubleRequest;
import com.alibaba.arms.mock.api.dto.ErrorTroubleRequest;
import com.alibaba.arms.mock.api.dto.SlowTroubleRequest;
import com.alibaba.arms.mock.server.service.ServiceTroubleInjectorManager;
import com.alibaba.arms.mock.server.service.trouble.ITrouble;
import com.alibaba.arms.mock.server.service.trouble.TroubleEnum;
import com.alibaba.arms.mock.server.service.trouble.TroubleManager;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.handler.IgnoreErrorsBindHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@RestController
@RequestMapping("/trouble/make")
@Api("故障注入")
@Slf4j
public class TroubleController {

    @Autowired
    private TroubleManager troubleManager;

    @Autowired
    private ServiceTroubleInjectorManager serviceTroubleInjectorManager;

    @Autowired
    private List<ITrouble> troubles;

    @ApiOperation("制造内存泄露")
    @RequestMapping(value = "/mem", method = RequestMethod.POST)
    public String mem(@ApiParam(value = "每一次申请内存的大小(单位字节)", defaultValue = "1048576") @RequestParam(required = false) Integer byteSize) {
        ITrouble trouble = troubleManager.getTroubleMaker(TroubleEnum.GC.getCode());
        Map<String, String> params = Maps.newHashMap();
        if (null != byteSize) {
            params.put("byteSize", String.valueOf(byteSize));
        }
        return trouble.make(params);
    }

    @ApiOperation("取消服务故障注入")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel(
            @ApiParam(value = "故障类型", allowableValues = "slow,error") @RequestParam("troubleCode") String troubleCode,
            @ApiParam(value = "组件名称", example = "mysql") @RequestParam(required = false, value = "componentName") String componentName) {
        if ("slow".equalsIgnoreCase(troubleCode)) {
            serviceTroubleInjectorManager.cancelInjectSlow(componentName);
        } else if ("error".equalsIgnoreCase(troubleCode)) {
            serviceTroubleInjectorManager.cancelInjectError(componentName);
        } else if ("big".equalsIgnoreCase(troubleCode)) {
            serviceTroubleInjectorManager.cancelInjectBig(componentName);
        }
        return "success";
    }

    @ApiOperation("取消其他故障注入")
    @RequestMapping(value = "/cancelJvmTrouble", method = RequestMethod.POST)
    public String cancelJvmTrouble(
            @ApiParam(value = "故障类型", allowableValues = "gc,jvmCPU,disk") @RequestParam("troubleCode") String troubleCode) {
        ITrouble trouble = troubleManager.getTroubleMaker(troubleCode);
        Map<String, String> params = new HashMap<>();
        return trouble.cancel(params);
    }


    @ApiOperation("制造CPU 100%")
    @RequestMapping(value = "/cpu", method = RequestMethod.POST)
    public String cpu() {
        log.info("make cpu trouble");
        ITrouble trouble = troubleManager.getTroubleMaker(TroubleEnum.JVM_CPU.getCode());
        return trouble.make(null);
    }

    @ApiOperation("制造磁盘疯狂写入场景")
    @RequestMapping(value = "/disk", method = RequestMethod.POST)
    public String disk() {
        ITrouble trouble = troubleManager.getTroubleMaker(TroubleEnum.JVM_CPU.getCode());
        return trouble.make(null);
    }

    @ApiOperation("获取受影响的资源列表")
    @RequestMapping(value = "/affected", method = RequestMethod.POST)
    public List<String> affectedResources() {
        List<String> affected = new ArrayList<>();
        troubles.forEach(e -> {
            affected.addAll(e.getAffectedResource());
        });
        return affected;
    }


    @ApiOperation("注入慢故障")
    @RequestMapping(value = "/slow", method = RequestMethod.POST)
    public String injectSlow(@ApiParam(value = "慢故障请求") @RequestBody SlowTroubleRequest slowTroubleRequest) {
        if (Strings.isNullOrEmpty(slowTroubleRequest.getSlowInSeconds()) || "null".equalsIgnoreCase(slowTroubleRequest.getSlowInSeconds())) {
            slowTroubleRequest.setSlowInSeconds("3");
        }
        Map<String, String> params = new HashMap<>();
        if (null != slowTroubleRequest.getSlowInSeconds()) {
            params.put("sleepSeconds", slowTroubleRequest.getSlowInSeconds());
        }
        if (null != slowTroubleRequest.getParams()) {
            params.putAll(slowTroubleRequest.getParams());
        }
        serviceTroubleInjectorManager.injectSlow(slowTroubleRequest.getComponentName(), params, slowTroubleRequest.getSlowInSeconds());
        return "success";
    }

    @ApiOperation("注入错误故障")
    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String injectError(@ApiParam(value = "错误故障请求") @RequestBody ErrorTroubleRequest errorTroubleRequest) {
        serviceTroubleInjectorManager.injectError(errorTroubleRequest.getComponentName(), errorTroubleRequest.getParams());
        return "success";
    }

    @ApiOperation("注入大key故障")
    @RequestMapping(value = "/big", method = RequestMethod.POST)
    public String injectBig(@ApiParam(value = "错误故障请求") @RequestBody BigKeyTroubleRequest bigKeyTroubleRequest) {
        serviceTroubleInjectorManager.injectBig(bigKeyTroubleRequest.getComponentName(), bigKeyTroubleRequest.getParams(), bigKeyTroubleRequest.getSize());
        return "success";
    }
}
