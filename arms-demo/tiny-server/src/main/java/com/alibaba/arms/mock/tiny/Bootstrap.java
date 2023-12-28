package com.alibaba.arms.mock.tiny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author aliyun
 * @date 2021/11/19
 */

@SpringBootApplication
@ServletComponentScan
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
