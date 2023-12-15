package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.BadSqlCase;
import com.alibaba.arms.demo.client.service.MysqlCase;
import com.alibaba.arms.demo.client.utils.InvocationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@RestController
@RequestMapping("/easy/mysql")
@Api("Mysql测试案例")
public class MysqlCaseController {
    @Autowired
    private MysqlCase mysqlCase;

    @Autowired
    private BadSqlCase badSqlCase;
    
    public static AtomicLong mysqlConnectionInjectTime = new AtomicLong(0);
    public static AtomicLong mysqlSlowInjectTime = new AtomicLong(0);
    
    @PostConstruct
    public void init() {
        try {
//            this.badSqlCase.run(0, 3,InvocationUtils.genMysqlInvocation());
            this.mysqlCase.run(0, 25,InvocationUtils.genMysqlInvocation());
            this.mysqlCase.run(0, 10,InvocationUtils.genMysqlConnectInvocation());
        } catch (Exception e) {
        }
    }
    
    @ApiOperation("启动")
    @RequestMapping(value = "/case/start", method = RequestMethod.POST)
    public String runMysql(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                   Integer durationSeconds,
                           @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                   Integer parallel
    ) {
//        return mysqlCase.run(durationSeconds, null == parallel ? 1 : parallel);
        return mysqlCase.run(durationSeconds, null == parallel ? 1 : parallel, InvocationUtils.genMysqlInvocation());
    }

    @ApiOperation("启动-BadSql")
    @RequestMapping(value = "/case/start-bad-sql", method = RequestMethod.POST)
    public String runBadsql(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                    Integer durationSeconds,
                            @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                    Integer parallel
    ) {
        mysqlConnectionInjectTime.set(System.currentTimeMillis());
        return badSqlCase.run(durationSeconds, null == parallel ? 1 : parallel);
    }


    @ApiOperation("停止流量注入")
    @RequestMapping(value = "/case/stop", method = RequestMethod.POST)
    public String stop() {
        return mysqlCase.stop();
    }

    @ApiOperation("停止-BadSql流量注入")
    @RequestMapping(value = "/case/stop-bad-sql", method = RequestMethod.POST)
    public String stopBadSql() {
        mysqlConnectionInjectTime.set(0);
        return badSqlCase.stop();
    }


    @ApiOperation("注入mysql错误")
    @RequestMapping(value = "/inject_mysql_error", method = RequestMethod.POST)
    public String injectMysqlError() {
        return mysqlCase.injectError();
    }

    @ApiOperation("终止注入mysql错误")
    @RequestMapping(value = "/stop_inject_mysql_error", method = RequestMethod.POST)
    public String stopInjectMysqlError() {
        return mysqlCase.stopInjectError();
    }


    @ApiOperation("注入mysql慢")
    @RequestMapping(value = "/inject_mysql_slow", method = RequestMethod.POST)
    public String injectMysqlSlow(
            @ApiParam(value = "slowSeconds", defaultValue = "3") @RequestParam(required = false, name = "slowSeconds")
                    Double slowSeconds
    ) {
        mysqlSlowInjectTime.set(System.currentTimeMillis());
        return mysqlCase.injectSlow(slowSeconds);
    }

    @ApiOperation("终止注入mysql慢")
    @RequestMapping(value = "/stop_inject_mysql_slow", method = RequestMethod.POST)
    public String stopInjectMysqlSlow() {
        mysqlSlowInjectTime.set(0);
        return mysqlCase.stopInjectSlow();
    }

    @ApiOperation("注入连接获取慢")
    @RequestMapping(value = "/inject_get_conn_slow", method = RequestMethod.POST)
    public String injectGetConnSlow(
            @ApiParam(value = "slowSeconds", defaultValue = "3") @RequestParam(required = false, name = "slowSeconds")
                    Double slowSeconds
    ) {
        return badSqlCase.injectSlow(slowSeconds);
    }

    @ApiOperation("终止注入连接获取慢")
    @RequestMapping(value = "/stop_inject_get_conn_slow", method = RequestMethod.POST)
    public String stopInjectRedisSlow(
    ) {
        return badSqlCase.stopInjectSlow();
    }

}
