package com.alibaba.arms.mock.server.service;

import org.springframework.stereotype.Component;

/**
 * @author aliyun
 * @date 2021/08/03
 */
@Component
public class DubboGreetingService extends AbstractComponent {
    @Override
    public String getComponentName() {
        return "dubbo";
    }

    @Override
    public void execute() {

    }

    @Override
    public Class getImplClass() {
        return DubboGreetingService.class;
    }
}
