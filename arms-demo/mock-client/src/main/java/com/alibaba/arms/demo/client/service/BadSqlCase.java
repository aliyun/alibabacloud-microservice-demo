package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author aliyun
 * @date 2021/10/29
 */
@Service
@Slf4j
public class BadSqlCase extends BaseTestCase {

    private final String troubleComponentName = "http";
    private String invocatonRaw = "\n" +
            "{\n" +
//            "  \"children\": [\n" +
//            "    {\n" +
//            "      \"children\": [\n" +
//            "        {\n" +
//            "          \"component\": \"bad_sql\",\n" +
//            "          \"method\": \"success\",\n" +
//            "          \"service\": \"sanmu-aiops-demo-server-2\"\n" +
//            "        }\n" +
//            "      ],\n" +
//            "      \"component\": \"local\",\n" +
//            "      \"method\": \"success\",\n" +
//            "      \"service\": \"sanmu-aiops-demo-server-1\"\n" +
//            "    }\n" +
//            "  ],\n" +
            "  \"component\": \"bad_sql\",\n" +
            "  \"method\": \"success\",\n" +
            "  \"service\": \"insights-mysql-connection\"\n" +
            "}\n";
    private Invocation invocation = JSON.parseObject(invocatonRaw, Invocation.class);
    private String caseName = "bad_sql";
    private String troubleService = "insights-mysql-connection";
    private String BAD_HTTP_SERVICE = "insights-http-server-1";

    @Override
    public String getCaseName() {
        return caseName;
    }

    @Override
    public String run(Integer durationSeconds, int parallel) {
        /**
         * 将http-service-1的耗时调高
         */
        this.injectSlow(BAD_HTTP_SERVICE, "local", String.valueOf(5));
        caseRunner.run(caseName, null, parallel, durationSeconds, invocation, 1d);
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

    @Override
    public String stop() {
        /**
         * 恢复http-service-1的rt
         */
        this.stopInjectSlow(BAD_HTTP_SERVICE, "local");
        return super.stop();
    }
}
