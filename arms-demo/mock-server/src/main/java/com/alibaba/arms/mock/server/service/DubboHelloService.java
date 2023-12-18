package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.api.IWorld;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author aliyun
 * @date 2021/08/03
 */
@Component
public class DubboHelloService extends AbstractComponent {

    @DubboReference(version = "1.0.0", check = false, timeout = 5000, injvm = false, retries = 0)
    private IWorld world;

    @Override
    public String getComponentName() {
        return "dubbo-hello";
    }

    @Override
    public void execute() {
        world.sayWorld("hello");
    }

    @Override
    public Class getImplClass() {
        return DubboHelloService.class;
    }
}
