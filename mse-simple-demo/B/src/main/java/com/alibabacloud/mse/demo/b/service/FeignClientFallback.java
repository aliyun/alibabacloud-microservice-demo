package com.alibabacloud.mse.demo.b.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallback implements FallbackFactory<FeignClientTest> {


    static class FeignClientTestFallback implements FeignClientTest {

        private final String finalMsg;

        public FeignClientTestFallback(String finalMsg) {
            this.finalMsg = finalMsg;
        }

        @Override
        public String feignTest1() {
            return finalMsg;
        }

        @Override
        public String feignTest2() {
            return finalMsg;
        }

        @Override
        public String feignTest3() {
            return finalMsg;
        }

        @Override
        public String feignTest4() {
            return finalMsg;
        }

        @Override
        public String feignTest5() {
            return finalMsg;
        }

        @Override
        public String feignTest6() {
            return finalMsg;
        }

        @Override
        public String feignTest7() {
            return finalMsg;
        }

        @Override
        public String feignTest8() {
            return finalMsg;
        }

        @Override
        public String feignTest9() {
            return finalMsg;
        }

        @Override
        public String feignTest10() {
            return finalMsg;
        }

        @Override
        public String feignTest11() {
            return finalMsg;
        }

        @Override
        public String feignTest12() {
            return finalMsg;
        }

        @Override
        public String feignTest13() {
            return finalMsg;
        }

        @Override
        public String feignTest14() {
            return finalMsg;
        }

        @Override
        public String feignTest15() {
            return finalMsg;
        }

        @Override
        public String feignTest16() {
            return finalMsg;
        }

        @Override
        public String feignTest17() {
            return finalMsg;
        }

        @Override
        public String feignTest18() {
            return finalMsg;
        }

        @Override
        public String feignTest19() {
            return finalMsg;
        }

        @Override
        public String feignTest20() {
            return finalMsg;
        }

        @Override
        public String feignTest21() {
            return finalMsg;
        }

        @Override
        public String feignTest22() {
            return finalMsg;
        }

        @Override
        public String feignTest23() {
            return finalMsg;
        }

        @Override
        public String feignTest24() {
            return finalMsg;
        }

        @Override
        public String feignTest25() {
            return finalMsg;
        }

        @Override
        public String feignTest26() {
            return finalMsg;
        }

        @Override
        public String feignTest27() {
            return finalMsg;
        }

        @Override
        public String feignTest28() {
            return finalMsg;
        }

        @Override
        public String feignTest29() {
            return finalMsg;
        }

        @Override
        public String feignTest30() {
            return finalMsg;
        }

        @Override
        public String feignTest31() {
            return finalMsg;
        }

        @Override
        public String feignTest32() {
            return finalMsg;
        }

        @Override
        public String feignTest33() {
            return finalMsg;
        }

        @Override
        public String feignTest34() {
            return finalMsg;
        }

        @Override
        public String feignTest35() {
            return finalMsg;
        }

        @Override
        public String feignTest36() {
            return finalMsg;
        }

        @Override
        public String feignTest37() {
            return finalMsg;
        }

        @Override
        public String feignTest38() {
            return finalMsg;
        }

        @Override
        public String feignTest39() {
            return finalMsg;
        }

        @Override
        public String feignTest40() {
            return finalMsg;
        }

        @Override
        public String feignTest41() {
            return finalMsg;
        }

        @Override
        public String feignTest42() {
            return finalMsg;
        }

        @Override
        public String feignTest43() {
            return finalMsg;
        }

        @Override
        public String feignTest44() {
            return finalMsg;
        }

        @Override
        public String feignTest45() {
            return finalMsg;
        }

        @Override
        public String feignTest46() {
            return finalMsg;
        }

        @Override
        public String feignTest47() {
            return finalMsg;
        }

        @Override
        public String feignTest48() {
            return finalMsg;
        }

        @Override
        public String feignTest49() {
            return finalMsg;
        }

        @Override
        public String feignTest50() {
            return finalMsg;
        }
    }

    //
    @Override
    public FeignClientTest create(Throwable throwable) {
        if (throwable == null) {
            return new FeignClientTestFallback("Unknown error");
        }
        if (throwable.getClass().getName().contains("com.alibaba.csp.sentinel")) {
            return new FeignClientTestFallback("Service is in abnormal status now, and block by mse!");
        }

        return new FeignClientTestFallback("Service is in abnormal status and throws exception by itself!");
    }
}
