package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.ComponentService;
import com.alibaba.arms.mock.server.service.RedisService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components/api/v1")
@Api("redis")
public class RedisController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/redis/success", method = RequestMethod.POST)
    public String success(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        redisService.execute();
        return componentService.execute(children, level);
    }
}


