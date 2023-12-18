package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author aliyun
 * @date 2021/10/29
 */
@Service
public class DubboChainCase extends BaseTestCase {

    private final String troubleComponentName = "dubbo-world";
    private String caseName = "dubbo-chain";

    /**
     * http->
     *      dubbo-hello
     *          dubbo-world
     */
    private String invocatonRaw = "\n" +
            "{\n" +
            "  \"component\": \"dubbo-chain\",\n" +
            "  \"method\": \"success\",\n" +
            "  \"service\": \"sanmu-aiops-demo-server-0\"\n" +
            "}";
    private Invocation invocation = JSON.parseObject(invocatonRaw, Invocation.class);
    private String troubleService = "sanmu-aiops-demo-server-0";
    @Autowired
    private CaseRunner caseRunner;

    @Override
    public String getCaseName() {
        return caseName;
    }

    @Override
    public String run(Integer durationSeconds, int parallel) {
//        caseRunner.run(caseName, null, parallel, durationSeconds, invocation, 1d);
        return caseName;
    }


    @Override
    public String injectError() {
        return this.injectError(troubleService, troubleComponentName);
    }

    @Override
    public String stopInjectError() {
        return this.stopInjectError(troubleService, troubleComponentName);
    }

    @Override
    public String injectSlow(Double slowSeconds) {
        return this.injectSlow(troubleService, troubleComponentName, String.valueOf(slowSeconds));
    }

    @Override
    public String stopInjectSlow() {
        return this.stopInjectSlow(troubleService, troubleComponentName);
    }

}
