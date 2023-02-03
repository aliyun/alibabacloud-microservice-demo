package com.alibabacloud.mse.demo.c.mq;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RocketMqConfiguration {

    @Value("${middleware.mq.address}")
    private String nameSrvAddr;

    @Value("${middleware.mq.ak}")
    private String mqak;
 
    @Value("${middleware.mq.sk}")
    private String mqsk;

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    static {
        System.setProperty("rocketmq.client.log.loadconfig", "false");
    }

    @Bean(destroyMethod = "shutdown")
    public Producer getBaseProducer() throws Exception {
        Properties properties = new Properties();
        log.info("mqak is {}", mqak);
        properties.setProperty(PropertyKeyConst.AccessKey, mqak);
        properties.setProperty(PropertyKeyConst.SecretKey, mqsk);
 
        properties.setProperty(PropertyKeyConst.GROUP_ID, groupName);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, nameSrvAddr);
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        log.info("完成启动rocketMq的producer");
        return producer;
    }


}

