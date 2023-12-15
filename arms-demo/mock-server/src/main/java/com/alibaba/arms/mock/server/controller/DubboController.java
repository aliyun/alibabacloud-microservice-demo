package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.ComponentService;
import com.alibaba.arms.mock.server.service.dubbo.DubboConsumerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components/api/v1")
@Api("dubbo")
@Slf4j
public class DubboController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private DubboConsumerService dubboConsumerService;

    @RequestMapping(value = "/dubbo/success", method = RequestMethod.POST)
    public String success(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        dubboConsumerService.execute();
        return componentService.execute(children, level);
    }


    /**
     * 内部失败时，依然返回200. 这样可以构造出异常突增，但是错误数没有变化的场景
     * @param children
     * @param level
     * @return
     */
    @RequestMapping(value = "/dubbo/ignore_exceptions", method = RequestMethod.POST)
    public String ignoreExceptions(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        try {
            dubboConsumerService.execute();
            return componentService.execute(children, level);
        } catch (Exception e) {
            log.error("execute failed", e);
        }
        return "success";
    }
}


