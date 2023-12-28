package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.CaseRunner;
import com.alibaba.arms.mock.api.Invocation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@RestController
@RequestMapping("/testcase")
@Api("自定义测试场景")
public class TestCaseController {

    @Autowired
    private CaseRunner caseRunner;

    @ApiOperation("执行自定义测试场景")
    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public String run(
            @ApiParam("用例名称") @RequestParam("name") String caseName,
            @ApiParam("调用链参数") @RequestBody Invocation invocation,
            @ApiParam("起始服务地址") @RequestParam("serverUrl") String serverUrl,
            @ApiParam(value = "持续时间") @RequestParam(required = false, name = "durationSeconds") Integer durationSeconds,
            @ApiParam(value = "qps(为0时表示连续发送)", defaultValue = "0") @RequestParam(required = false, name = "qps") Double qps,
            @ApiParam("并行度") @RequestParam(name = "parallel") Integer parallel
    ) {
        return caseRunner.run(caseName, serverUrl, parallel, null == durationSeconds ? Integer.MAX_VALUE : durationSeconds, invocation, null == qps ? 0d : qps);
    }

    @ApiOperation("停止测试案例")
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public String stop(
            @ApiParam("用例名称") @RequestParam("name") String caseName) {
        return caseRunner.stop(caseName);
    }


    @ApiOperation("列出所有执行用例")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<String> list() {
        return caseRunner.list();
    }
}
