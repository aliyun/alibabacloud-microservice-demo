package com.alibaba.arms.mock.server.controller;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.arms.mock.server.service.BadSqlService;
import com.alibaba.arms.mock.server.service.ComponentService;
import com.alibaba.arms.mock.server.service.MysqlService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components/api/v1")
@Api("badSql")
public class BadSqlController {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private BadSqlService badSqlService;

    @RequestMapping(value = "/bad_sql/success", method = RequestMethod.POST)
    public String success(@RequestBody(required = false) List<Invocation> children, @RequestParam(required = false, value = "level") Integer level) {
        badSqlService.execute();
        return componentService.execute(children, level);
    }
}



