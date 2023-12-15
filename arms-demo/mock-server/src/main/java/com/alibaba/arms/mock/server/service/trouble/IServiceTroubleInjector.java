package com.alibaba.arms.mock.server.service.trouble;

import java.util.Map;

/**
 * @author aliyun
 * @date 2022/02/08
 */
public interface IServiceTroubleInjector {

    void injectError(Map<String, String> params);

    void cancelInjectError();

    void injectSlow(Map<String, String> params, String seconds);

    void cancelInjectSlow();

    String getComponentName();

    default void injectBig(Map<String, String> params, String size){};

    default void cancelInjectBig(){};
}
