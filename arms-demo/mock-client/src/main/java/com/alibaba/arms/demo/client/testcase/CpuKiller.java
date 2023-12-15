package com.alibaba.arms.demo.client.testcase;

import com.alibaba.arms.mock.api.Invocation;

/**
 * @author aliyun
 * @date 2021/07/09
 */
public class CpuKiller implements ICase {

    @Override
    public void init() {

    }

    @Override
    public Invocation nextOne() {
        Invocation invocation = new Invocation();
        invocation.setService("arms-mock-dev-0");
        invocation.setComponent("local");
        invocation.setMethod("busy");
        return invocation;
    }
}
