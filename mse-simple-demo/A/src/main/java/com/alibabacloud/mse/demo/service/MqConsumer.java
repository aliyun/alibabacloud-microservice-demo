package com.alibabacloud.mse.demo.service;

//import com.pingpongx.ocean.context.BusinessContext;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

/**
 * @author zhujy
 */
@Slf4j
@Component
public class MqConsumer implements MessageListenerConcurrently {

    @SneakyThrows
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            MessageExt messageExt = list.get(0);
            String topic = messageExt.getTopic();
            String tags = messageExt.getTags();
            String messageString = new String(messageExt.getBody(), StandardCharsets.UTF_8);

            log.info("topic:{},tags:{},messageString:{}", topic, tags, messageString);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } finally {
//            BusinessContext.clear();
        }
    }

}
