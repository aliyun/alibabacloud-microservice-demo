package com.alibabacloud.hipsershop.mockprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.alibabacloud.hipsershop.mockprovider")
public class MockApp {

    public static void main(String[] args) {
        SpringApplication.run(MockApp.class, args);
    }
}
