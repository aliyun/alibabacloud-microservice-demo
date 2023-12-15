package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.server.service.dubbo.DubboChainDirectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/components/api/v1")
@Api("dubbo-chain")
public class DubboChainController {

    @Autowired
    private DubboChainDirectService dubboChainDirectService;

    @RequestMapping(value = "/dubbo-chain/success", method = RequestMethod.POST)
    public String success() {
        dubboChainDirectService.execute();
        return "success";
    }
}


