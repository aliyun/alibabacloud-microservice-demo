package com.alibabacloud.mse.demo.a.mq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

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

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private InetUtils inetUtils;

    @Autowired
    private String serviceTag;

    static {
        System.setProperty("rocketmq.client.log.loadconfig", "false");
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Consumer mqPushConsumer() {
        log.info("正在启动rocketMq的consumer");
        log.info("mqak is {}", mqak);
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.GROUP_ID, groupName);
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, mqak);
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, mqsk);
        consumerProperties.setProperty(PropertyKeyConst.NAMESRV_ADDR, nameSrvAddr);
        Consumer consumer = ONSFactory.createConsumer(consumerProperties);

        MqMessageListener mqMessageListener = new MqMessageListener(
                restTemplate,
                inetUtils,
                serviceTag
        );
        consumer.subscribe(topic, "*", mqMessageListener);
        log.info("完成启动rocketMq的consumer,subscribe:{}", topic);
        return consumer;
    }
}