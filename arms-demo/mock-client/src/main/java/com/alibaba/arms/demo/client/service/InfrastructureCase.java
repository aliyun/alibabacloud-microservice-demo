package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.mock.api.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureCase extends BaseTestCase{
    private final String componentName = "local";
    private String caseName = "local";

    @Autowired
    private CaseRunner caseRunner;
    
    @Override
    public String getCaseName() {
        return null;
    }

    @Override
    public String run(Integer durationSeconds, int parallel) {
        return null;
    }

    @Override
    public String run(Integer durationSeconds, int parallel, Invocation invocation) {
        caseRunner.run(caseName, null, parallel, durationSeconds, invocation, 1d, true);
        return caseName;
    }

    @Override
    public String injectError() {
        return null;
    }

    @Override
    public String stopInjectError() {
        return null;
    }

    @Override
    public String injectSlow(Double slowSeconds) {
        return null;
    }

    @Override
    public String stopInjectSlow() {
        return null;
    }
}
