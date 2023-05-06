package com.alibabacloud.mse.demo.a.service;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallback implements FallbackFactory<FeignClientTest> {

    static class FeignClientTestFallback implements FeignClientTest {

        private final String finalMsg;

        public FeignClientTestFallback(String finalMsg) {
            this.finalMsg = finalMsg;
        }

        @Override
        public String bByFeign(String s) {
            return finalMsg;
        }

        @Override
        public String circuit_breaker_rt_b() {
            return finalMsg;
        }

        @Override
        public String circuit_breaker_exception_b() {
            return finalMsg;
        }
    }

    @Override
    public FeignClientTest create(Throwable throwable) {
        if (throwable == null) {
            return new FeignClientTestFallback("Unknown error");
        }
        if (throwable.getClass().getName().contains("com.alibaba.csp.sentinel")) {
            return new FeignClientTestFallback("Service is in abnormal status now, and block by mse circuit rule!");
        }

        return new FeignClientTestFallback("Service is in abnormal status and throws exception by itself!");
    }
}
