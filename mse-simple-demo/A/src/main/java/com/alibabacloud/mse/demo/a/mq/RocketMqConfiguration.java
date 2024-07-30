package com.alibabacloud.mse.demo.a.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RocketMqConfiguration {

    @Value("${middleware.mq.address}")
    private String nameSrvAddr;

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
    public DefaultMQPushConsumer mqPushConsumer() throws MQClientException {
        log.info("正在启动rocketMq的consumer");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameSrvAddr);
        consumer.subscribe(topic, "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        MqConsumer mqConsumer = new MqConsumer(
                restTemplate,
                inetUtils,
                serviceTag
        );
        consumer.registerMessageListener(mqConsumer);
        log.info("完成启动rocketMq的consumer,subscribe:{}", topic);
        return consumer;
    }
}
