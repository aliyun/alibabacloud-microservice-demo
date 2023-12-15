package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.api.IHello;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author aliyun
 * @date 2021/08/03
 */
@Component
public class DubboChainService extends AbstractComponent {

    @DubboReference(version = "1.0.0", check = false, timeout = 5000, injvm = false, retries = 0)
    private IHello hello;

    @Override
    public String getComponentName() {
        return "dubbo-chain";
    }

    @Override
    public void execute() {
        hello.sayHello("hello");
    }

    @Override
    public Class getImplClass() {
        return DubboChainService.class;
    }
}
