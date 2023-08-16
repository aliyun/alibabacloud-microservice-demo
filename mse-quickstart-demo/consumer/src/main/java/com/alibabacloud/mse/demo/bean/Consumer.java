package com.alibabacloud.mse.demo.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author : zhaofei
 * @create 2023/8/16 14:00
 */
@Component
@RefreshScope
@ConfigurationProperties()
public class Consumer {
    private String name = "123";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
