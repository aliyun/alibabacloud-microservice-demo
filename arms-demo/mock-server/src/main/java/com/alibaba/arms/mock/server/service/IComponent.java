package com.alibaba.arms.mock.server.service;

/**
 * @author aliyun
 * @date 2021/06/10
 */
public interface IComponent {

    String getComponentName();

    /**
     * 成功场景
     */
    void execute();

    Class getImplClass();
}
