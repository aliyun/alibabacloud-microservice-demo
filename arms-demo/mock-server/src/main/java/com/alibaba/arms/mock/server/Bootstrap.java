package com.alibaba.arms.mock.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.alibaba.arms.mock.dao.mapper")
@EnableOpenApi
@EnableAutoConfiguration
public class Bootstrap {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run() {
                super.run();
            }
        });
        SpringApplication.run(Bootstrap.class, args);
    }
}
