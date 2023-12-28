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
public class HttpCase extends BaseTestCase {

    private final String componentName = "http";
    private String caseName = "http";
    private String invocatonRaw = "\n" +
            "{\n" +
            "  \"children\": [\n" +
            "    {\n" +
            "      \"children\": [\n" +
            "        {\n" +
            "          \"component\": \"http\",\n" +
            "          \"method\": \"success\",\n" +
            "          \"service\": \"insights-dubbo-server-2\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"component\": \"dubbo\",\n" +
            "      \"method\": \"success\",\n" +
            "      \"service\": \"insights-dubbo-server-1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"component\": \"local\",\n" +
            "      \"method\": \"success\",\n" +
            "      \"service\": \"insights-dubbo-server-1\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"component\": \"local\",\n" +
            "  \"method\": \"success\",\n" +
            "  \"service\": \"insights-dubbo-server-0\"\n" +
            "}";
    private Invocation invocation = JSON.parseObject(invocatonRaw, Invocation.class);
    private String troubleService = "insights-server-3";
    @Autowired
    private CaseRunner caseRunner;

    @Override
    public String getCaseName() {
        return caseName;
    }

    @Override
    public String run(Integer durationSeconds, int parallel) {
        caseRunner.run(caseName, null, parallel, durationSeconds, invocation, 1d);
        return caseName;
    }
    
    @Override
    public String run(Integer durationSeconds, int parallel, Invocation invocation) {
        caseRunner.run(caseName, null, parallel, durationSeconds, invocation, 1d);
        return caseName;
    }


    @Override
    public String injectError() {
        return this.injectError(troubleService, componentName);
    }

    @Override
    public String stopInjectError() {
        return this.stopInjectError(troubleService, componentName);
    }

    @Override
    public String injectSlow(Double slowSeconds) {
        return this.injectSlow(troubleService, componentName, String.valueOf(slowSeconds));
    }

    @Override
    public String stopInjectSlow() {
        return this.stopInjectSlow(troubleService, componentName);
    }

}
