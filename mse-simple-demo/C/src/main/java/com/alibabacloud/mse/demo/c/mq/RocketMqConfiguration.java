package com.alibabacloud.mse.demo.c.mq;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RocketMqConfiguration {

    @Value("${middleware.mq.address}")
    private String nameSrvAddr;

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    @Value("${middleware.mq.ak:}")
    private String ak;
    @Value("${middleware.mq.sk:}")
    private String sk;

    static {
        System.setProperty("rocketmq.client.log.loadconfig", "false");
    }

    @Bean(destroyMethod = "shutdown")
    public DefaultMQProducer getBaseProducer() throws Exception {
        log.info("正在启动rocketMq的producer");

        DefaultMQProducer baseProducer;
        if (StringUtils.isNotEmpty(this.ak) && StringUtils.isNotEmpty(this.sk)) {
            log.info("middleware.mq.ak 不为空,MQ ACL信息已填充");
            RPCHook rpcHook = new AclClientRPCHook(new SessionCredentials(this.ak, this.sk));
            baseProducer = new DefaultMQProducer(rpcHook);
        } else {
            log.info("middleware.mq.ak 为空");
            baseProducer = new DefaultMQProducer();
        }

        baseProducer.setProducerGroup(groupName);
        baseProducer.setNamesrvAddr(nameSrvAddr);
        baseProducer.start();
        log.info("完成启动rocketMq的producer");
        return baseProducer;
    }


}

