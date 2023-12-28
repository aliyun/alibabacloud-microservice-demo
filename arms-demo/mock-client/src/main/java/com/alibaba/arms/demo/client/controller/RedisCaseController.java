package com.alibaba.arms.demo.client.controller;

import com.alibaba.arms.demo.client.service.RedisCase;
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
@RequestMapping("/easy/redis")
@Api("Redis测试案例")
public class RedisCaseController {

    @Autowired
    private RedisCase redisCase;

    public static AtomicLong redisInjectTime = new AtomicLong(0);
    
    @PostConstruct
    public void init() {
        try {
            this.redisCase.run(0, 3, InvocationUtils.genRedisInvocation());
        } catch (Exception e) {
        }
    }

    @ApiOperation("启动")
    @RequestMapping(value = "/case/start", method = RequestMethod.POST)
    public String runRedis(@ApiParam(value = "持续时间", defaultValue = "0") @RequestParam(required = false, name = "durationSeconds")
                                   Integer durationSeconds,
                           @ApiParam(value = "并发度", defaultValue = "1") @RequestParam(required = false, name = "parallel")
                                   Integer parallel) {
        redisInjectTime.set(System.currentTimeMillis());
        return redisCase.run(durationSeconds, null == parallel ? 1 : parallel, InvocationUtils.genRedisInvocation());
    }

    @ApiOperation("停止流量注入")
    @RequestMapping(value = "/case/stop", method = RequestMethod.POST)
    public String runDubbo() {
        return redisCase.stop();
    }

    @ApiOperation("减小流量")
    public String slowDown() {
        redisCase.stop();
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            
        }
        return this.redisCase.run(0, 3, InvocationUtils.genRedisInvocation());
    }

    @ApiOperation("注入Redis错误")
    @RequestMapping(value = "/inject_redis_error", method = RequestMethod.POST)
    public String injectRedisError() {
        return redisCase.injectError();
    }

    @ApiOperation("终止注入Redis错误")
    @RequestMapping(value = "/stop_inject_redis_error", method = RequestMethod.POST)
    public String stopInjectRedisError() {
        return redisCase.stopInjectError();
    }


    @ApiOperation("注入Redis慢")
    @RequestMapping(value = "/inject_redis_slow", method = RequestMethod.POST)
    public String injectRedisSlow(
            @ApiParam(value = "slowSeconds", defaultValue = "3") @RequestParam(required = false, name = "slowSeconds")
                    Double slowSeconds
    ) {
        return redisCase.injectSlow(slowSeconds);
    }

    @ApiOperation("终止注入Redis慢")
    @RequestMapping(value = "/stop_inject_redis_slow", method = RequestMethod.POST)
    public String stopInjectRedisSlow(
    ) {
        return redisCase.stopInjectSlow();
    }

    @ApiOperation("注入Redis大key")
    @RequestMapping(value = "/inject_redis_big", method = RequestMethod.POST)
    public String injectRedisBigKey(
            @ApiParam(value = "size", defaultValue = "3") @RequestParam(required = false, name = "size")
                    Double size
    ) {
        return redisCase.injectBig(size);
    }

    @ApiOperation("终止注入Redis大key")
    @RequestMapping(value = "/stop_inject_redis_big", method = RequestMethod.POST)
    public String stopInjectRedisBigKey(
    ) {
        return redisCase.stopInjectBig();
    }
}
