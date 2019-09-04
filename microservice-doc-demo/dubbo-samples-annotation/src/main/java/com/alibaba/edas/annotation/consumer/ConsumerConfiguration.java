package com.alibaba.edas.annotation.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = "com.alibaba.edas.annotation.consumer")
@PropertySource("classpath:/spring/dubbo-consumer.properties")
@ComponentScan(value = {"com.alibaba.edas.annotation.consumer"})
public class ConsumerConfiguration {

}
