package com.alibaba.arms.demo.client.testcase;

import com.alibaba.arms.mock.api.Invocation;

/**
 * @author aliyun
 * @date 2021/07/09
 */
public interface ICase {

    void init();

    Invocation nextOne();
}
