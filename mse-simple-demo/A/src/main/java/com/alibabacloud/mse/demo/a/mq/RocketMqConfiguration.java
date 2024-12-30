package com.alibabacloud.mse.demo.a.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${middleware.mq.ak:}")
    private String ak;
    @Value("${middleware.mq.sk:}")
    private String sk;

    @Autowired
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

        DefaultMQPushConsumer consumer;
        if (StringUtils.isNotEmpty(this.ak) && StringUtils.isNotEmpty(this.sk)) {
            log.info("middleware.mq.ak 不为空,MQ ACL信息已填充");
            RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(this.ak, this.sk));
            consumer = new DefaultMQPushConsumer(this.groupName, rpcHook, new AllocateMessageQueueAveragely());
        } else {
            log.info("middleware.mq.ak 为空");
            consumer = new DefaultMQPushConsumer(groupName);
        }
        consumer.setNamesrvAddr(nameSrvAddr);
        consumer.subscribe(topic, "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        MqConsumer mqConsumer = new MqConsumer(restTemplate, inetUtils, serviceTag);
        consumer.registerMessageListener(mqConsumer);
        log.info("完成启动rocketMq的consumer,subscribe:{}", topic);
        return consumer;
    }
}
