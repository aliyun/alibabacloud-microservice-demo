package com.alibaba.arms.mock.server.service.trouble;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author aliyun
 * @date 2021/07/19
 */

@Aspect
@Component
@Slf4j
@Activate
public class DefaultSlowTroubleInjector extends DirectTrouble {

    @Override
    public void doMakeTrouble(Map<String, String> params) {
        double sleepSeconds = params == null ? 5 : Double.valueOf(params.getOrDefault("sleepSeconds", "5"));
        try {
            Thread.sleep((long) (sleepSeconds * 1000));
        } catch (InterruptedException e) {
            log.error("interrupt", e);
        }
    }

    @Override
    public TroubleEnum getTroubleType() {
        return TroubleEnum.SLOW;
    }

}
