package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.AsyncService;
import com.alibaba.arms.mock.server.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author aliyun
 * @date 2021/06/22
 */
@RestController
@RequestMapping("/components/api/v1")
public class AsyncController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private AsyncService asyncService;

    @RequestMapping(value = "/async/success", method = RequestMethod.POST)
    public String success(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") int level) {
        asyncService.execute();
        return componentService.execute(children, level);
    }
}
