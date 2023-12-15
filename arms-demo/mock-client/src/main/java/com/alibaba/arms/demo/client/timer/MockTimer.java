package com.alibaba.arms.demo.client.timer;

import com.alibaba.arms.demo.client.controller.HttpCaseController;
import com.alibaba.arms.demo.client.controller.MysqlCaseController;
import com.alibaba.arms.demo.client.controller.RedisCaseController;
import com.alibaba.arms.demo.client.service.BadSqlCase;
import com.alibaba.arms.demo.client.service.HttpCase;
import com.alibaba.arms.demo.client.service.MysqlCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class MockTimer {
    @Autowired
    HttpCase httpCase;
    
    @Autowired
    MysqlCase mysqlCase;
    
    @Autowired
    BadSqlCase badSqlCase;
    
    @Autowired
    RedisCaseController redisCaseController;

    private static final long DEFAULT_DURATION = 8 * 60 * 1000L;

    // 检测注入故障标记位默认5分钟就自动停止
    @Scheduled(cron = "0 0/1 * * * ?")
    public void checkErrorTimer() {
        long now = System.currentTimeMillis();
        log.info("httpSlowInjectTime:{} httpErrorInjectTime:{} " +
                "mysqlSlowInjectTime:{} mysqlConnectionInjectTime:{}", 
                HttpCaseController.httpSlowInjectTime.get(),HttpCaseController.httpErrorInjectTime.get(),
                MysqlCaseController.mysqlSlowInjectTime.get(), MysqlCaseController.mysqlConnectionInjectTime.get());
        if (HttpCaseController.httpSlowInjectTime.get() != 0 && 
                now - HttpCaseController.httpSlowInjectTime.get() > DEFAULT_DURATION) {
            httpCase.stopInjectSlow();
        }
        if (HttpCaseController.httpErrorInjectTime.get() != 0 &&
                now - HttpCaseController.httpErrorInjectTime.get() > DEFAULT_DURATION) {
            httpCase.stopInjectError();
        }
        if (MysqlCaseController.mysqlSlowInjectTime.get() != 0 &&
                now - MysqlCaseController.mysqlSlowInjectTime.get() > DEFAULT_DURATION) {
            mysqlCase.stopInjectSlow();
        }
        if (MysqlCaseController.mysqlConnectionInjectTime.get() != 0 &&
                now - MysqlCaseController.mysqlConnectionInjectTime.get() > DEFAULT_DURATION) {
            badSqlCase.stop();
        }
        if (RedisCaseController.redisInjectTime.get() != 0 &&
                now - RedisCaseController.redisInjectTime.get() > DEFAULT_DURATION) {
            redisCaseController.slowDown();
        }
    }
}
