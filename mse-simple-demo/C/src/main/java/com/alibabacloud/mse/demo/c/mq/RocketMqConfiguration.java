package com.alibabacloud.mse.demo.c.mq;

/**
 * @author wangbb
 * @version 1.0
 * @description: rocketmq容器加载
 * @date 2021/8/10 10:10 上午
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhujy
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RocketMqConfiguration {

    @Value("${middleware.mq.address}")
    private String nameSrvAddr;

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    @Value("${rocketmq.ak}")
    private String ak;

    @Value("${rocketmq.sk}")
    private String sk;

    @Value("${rocketmq.namespace}")
    private String namespace;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    static {
        System.setProperty("rocketmq.client.log.loadconfig", "false");
    }

    @Bean(destroyMethod = "close")
    public Producer getBaseProducer() throws Exception {
        log.info("正在启动rocketMq的producer");

        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(nameSrvAddr).setNamespace(namespace);
        /**
         * 如果是使用公网接入点访问，configuration还需要设置实例的用户名和密码。用户名和密码在控制台访问控制的智能身份识别页签中获取。
         * 如果是在阿里云ECS内网访问，无需填写该配置，服务端会根据内网VPC信息智能获取。
         * 如果实例类型为Serverless实例，公网访问必须设置实例的用户名密码，当开启内网免身份识别时,内网访问可以不设置用户名和密码。
         */
        builder.setCredentialProvider(new StaticSessionCredentialsProvider(ak, sk));
        ClientConfiguration configuration = builder.build();

        ClientServiceProvider provider = ClientServiceProvider.loadService();
        Producer producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();

        log.info("完成启动rocketMq的producer");
        return producer;
    }


}

