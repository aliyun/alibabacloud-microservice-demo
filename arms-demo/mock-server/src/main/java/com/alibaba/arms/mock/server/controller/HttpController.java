package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.ComponentService;
import com.alibaba.arms.mock.server.service.HttpService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components/api/v1")
@Api("http")
@Slf4j
public class HttpController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private HttpService httpService;

    @RequestMapping(value = "/http/success", method = RequestMethod.POST)
    public String success(@RequestBody(required = false) List<Invocation> children,
                          @RequestParam(required = false, value = "level") Integer level) {
        httpService.execute();
        return componentService.execute(children, level);
    }
}


