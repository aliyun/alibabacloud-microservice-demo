package com.alibabacloud.hipstershop.currencyservice;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.alibabacloud.hipstershop.currencyservice")
public class CurrencyserviceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyserviceProviderApplication.class, args);
    }

}
