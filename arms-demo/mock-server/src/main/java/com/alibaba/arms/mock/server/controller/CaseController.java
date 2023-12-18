package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.ComponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author aliyun
 * @date 2021/06/10
 */
@RestController
@RequestMapping("/case/api/v1")
@Slf4j
public class CaseController {
    @Autowired
    private ComponentService componentService;

    @RequestMapping(value = "/http/execute", method = RequestMethod.POST)
    public String httpExecute(HttpServletRequest httpServletRequest, @RequestBody Invocation invocation, @RequestParam("level") int level) {
        log.info("execute {}", httpServletRequest.getRequestURL());
        return componentService.execute(Arrays.asList(invocation), level);
    }

    @RequestMapping(value = "/mysql/execute", method = RequestMethod.POST)
    public String mysqlExecute(HttpServletRequest httpServletRequest, @RequestBody Invocation invocation, @RequestParam("level") int level) {
        log.info("execute {}", httpServletRequest.getRequestURL());
        return componentService.execute(Arrays.asList(invocation), level);
    }

    @RequestMapping(value = "/redis/execute", method = RequestMethod.POST)
    public String redisExecute(HttpServletRequest httpServletRequest, @RequestBody Invocation invocation, @RequestParam("level") int level) {
        log.info("execute {}", httpServletRequest.getRequestURL());
        return componentService.execute(Arrays.asList(invocation), level);
    }

    @RequestMapping(value = "/local/execute", method = RequestMethod.POST)
    public String localExecute(HttpServletRequest httpServletRequest, @RequestBody Invocation invocation, @RequestParam("level") int level) {
        log.info("execute {}", httpServletRequest.getRequestURL());
        return componentService.execute(Arrays.asList(invocation), level);
    }

    @RequestMapping(value = "/bad_sql/execute", method = RequestMethod.POST)
    public String badSqlExecute(HttpServletRequest httpServletRequest, @RequestBody Invocation invocation, @RequestParam("level") int level) {
        log.info("execute {}", httpServletRequest.getRequestURL());
        return componentService.execute(Arrays.asList(invocation), level);
    }
}
