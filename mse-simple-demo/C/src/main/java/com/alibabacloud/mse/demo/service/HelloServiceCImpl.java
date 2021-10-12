package com.alibabacloud.mse.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;

import java.nio.charset.StandardCharsets;
import static com.alibabacloud.mse.demo.CApplication.SERVICE_TAG;

@Slf4j
@DubboService(version = "1.0.0")
@RequiredArgsConstructor
public class HelloServiceCImpl implements HelloServiceC {

    private final DefaultMQProducer producer;

    @Autowired
    InetUtils inetUtils;

    @Value("${rocketmq.consumer.topic}")
    private String topic;



    @Override
    public String hello(String name) {

        String value = "C" + SERVICE_TAG + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";

        try {
            Message msg = new Message();
            msg.setTopic(topic);
            msg.setTags(SERVICE_TAG);
            msg.setBody(value.getBytes(StandardCharsets.UTF_8));
            producer.send(msg);
        } catch (Exception e) {
            log.error("error:", e);
        }

        return value;
    }

}
