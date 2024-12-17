package com.alibabacloud.mse.demo.a.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.SessionCredentialsProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

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

    @Value("${rocketmq.ak}")
    private String ak;

    @Value("${rocketmq.sk}")
    private String sk;

    @Value("${rocketmq.namespace}")
    private String namespace;

    private final MqConsumer mqConsumer;

    static {
        System.setProperty("rocketmq.client.log.loadconfig", "false");
    }


    @Bean(destroyMethod = "close")
    public PushConsumer mqPushConsumer() throws Exception {
        log.info("正在启动rocketMq的consumer");

        final ClientServiceProvider provider = ClientServiceProvider.loadService();

        // Credential provider is optional for client configuration.
        SessionCredentialsProvider sessionCredentialsProvider =
                new StaticSessionCredentialsProvider(ak, sk);

        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(nameSrvAddr)
                .setNamespace(namespace)
                // On some Windows platforms, you may encounter SSL compatibility issues. Try turning off the SSL option in
                // client configuration to solve the problem please if SSL is not essential.
                // .enableSsl(false)
                .setCredentialProvider(sessionCredentialsProvider)
                .build();

        FilterExpression filterExpression = new FilterExpression();

        // In most case, you don't need to create too many consumers, singleton pattern is recommended.
        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                // Set the consumer group name.
                .setConsumerGroup(groupName)
                // Set the subscription for the consumer.
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                .setMessageListener(mqConsumer)
                .build();

        return pushConsumer;
    }
}
