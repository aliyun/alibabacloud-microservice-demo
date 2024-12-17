package com.alibabacloud.mse.demo.a.mq;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.MessageListener;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zhujy
 */
@Slf4j
@Component
public class MqConsumer implements MessageListener {

    @SneakyThrows
    @Override
    public ConsumeResult consume(MessageView messageView) {
        try{

            String topic = messageView.getTopic();
            String tags = messageView.getTag().orElse(null);
            String userProperty = messageView.getProperties().get("__microservice_tag__");

            ByteBuffer bodyBuffer = messageView.getBody();
            String value = StandardCharsets.UTF_8.decode(bodyBuffer).toString();


            log.info("messageView:{}, topic:{},tags:{},messageString:{},userProperty:{}", messageView, topic, tags, value, userProperty);

            return ConsumeResult.SUCCESS;
        } catch (Throwable t){
            t.printStackTrace();
            return ConsumeResult.SUCCESS;
        }
    }
}
