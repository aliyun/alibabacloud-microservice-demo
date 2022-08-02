package com.alibabacloud.mse.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.alibabacloud.mse.demo.AApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MqConsumer implements MessageListenerConcurrently {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String servcieTag;

    @SneakyThrows
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            MessageExt messageExt = list.get(0);
            String topic = messageExt.getTopic();
            String messageString = new String(messageExt.getBody(), StandardCharsets.UTF_8);
            String result =  "A"+ servcieTag+"[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                    restTemplate.getForObject("http://sc-B/b", String.class);

            log.info("topic:{},producer:{},invoke result:{}", topic, messageString, result);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } finally {
//            BusinessContext.clear();
        }
    }

}
