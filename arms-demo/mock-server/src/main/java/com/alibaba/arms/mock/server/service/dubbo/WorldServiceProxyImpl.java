package com.alibaba.arms.mock.server.service.dubbo;

import com.alibaba.arms.mock.api.IWorld;
import com.alibaba.arms.mock.server.service.DubboWorldService;
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
public class WorldServiceProxyImpl implements IWorld {

    @Autowired
    private DubboWorldService dubboWorldService;

    @PostConstruct
    public void init() {
        log.info("init dubbo world service");
    }

    @Override
    public String sayWorld(String name) {
        dubboWorldService.execute();
        return "hi, " + name;
    }
}
