package com.alibaba.arms.mock.server.service.dubbo;

import com.alibaba.arms.mock.api.IHello;
import com.alibaba.arms.mock.server.service.DubboHelloService;
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
public class HelloServiceProxyImpl implements IHello {

    @Autowired
    private DubboHelloService dubboHelloService;

    @PostConstruct
    public void init() {
        log.info("init dubbo hello service");
    }

    @Override
    public String sayHello(String name) {
        dubboHelloService.execute();
        return "hi, " + name;
    }
}
