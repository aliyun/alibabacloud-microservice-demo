package com.alibaba.arms.demo.client;

import com.alibaba.arms.mock.api.Invocation;

import java.util.Map;

public interface DynamicParamsCall {

    /**
     * 使用新的参数构造新的Invocation对象
     *
     * @param nextParams
     * @return
     */
    Invocation nextInvocation(Map<String, String> nextParams);
}
