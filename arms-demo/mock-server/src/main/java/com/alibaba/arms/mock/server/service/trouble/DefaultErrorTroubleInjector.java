package com.alibaba.arms.mock.server.service.trouble;

import com.alibaba.arms.mock.server.service.trouble.DirectTrouble;
import com.alibaba.arms.mock.server.service.trouble.TroubleEnum;
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
public class DefaultErrorTroubleInjector extends DirectTrouble {

    @Override
    protected void doMakeTrouble(Map<String, String> params) {
        throw new RuntimeException("here we made an error on purpose");
    }

    @Override
    public TroubleEnum getTroubleType() {
        return TroubleEnum.ERROR;
    }
}
