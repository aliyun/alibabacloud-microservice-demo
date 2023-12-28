package com.alibaba.arms.mock.server.service.dubbo;

import com.alibaba.arms.mock.api.IGreeting;
import com.alibaba.arms.mock.server.service.DubboGreetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.annotation.PostConstruct;

/**
 * @author aliyun
 * @date 2021/07/22
 */
@ConditionalOnProperty(value = "dubbo.service.enable", havingValue = "true", matchIfMissing = true)
@DubboService(version = "1.0.0")
@Slf4j
public class GreetingServiceProxyImpl implements IGreeting {

    @Autowired
    private DubboGreetingService dubboGreetingService;

    @PostConstruct
    public void init() {
        log.info("init dubbo service");
    }

    @Override
    public String sayHi(String name) {
        /**
         * 故障注入发生在dubboGreetingService中
         */
        dubboGreetingService.execute();
        return "hi, " + name;
    }
}
