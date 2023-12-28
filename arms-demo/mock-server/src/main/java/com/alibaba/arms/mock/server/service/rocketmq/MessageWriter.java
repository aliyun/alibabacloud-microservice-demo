package com.alibaba.arms.mock.server.service.rocketmq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author aliyun
 * @date 2021/01/19
 */
@Component
@ConditionalOnProperty(value = "rocketmq.producer.enable", havingValue = "true", matchIfMissing = false)
@Slf4j
public class MessageWriter {

    @Autowired
    private ProducerBean producerBean;

    @Autowired
    private MqConfig mqConfig;

    @PostConstruct
    public void init() {

//        Thread writer = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                writeMessage(producerBean, mqConfig);
//            }
//        });
//        writer.start();
    }

    public String writeMessage() {
        Message msg = new Message( //
                // Message所属的Topic
                mqConfig.getTopic(),
                // Message Tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在MQ服务器过滤
                mqConfig.getTag(),
                // Message Body 可以是任何二进制形式的数据， MQ不做任何干预
                // 需要Producer与Consumer协商好一致的序列化和反序列化方式
                "Hello MQ".getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一
        // 以方便您在无法正常收到消息情况下，可通过MQ 控制台查询消息并补发
        // 注意：不设置也不会影响消息正常收发
        msg.setKey("ORDERID_100" + System.currentTimeMillis());
        // 发送消息，只要不抛异常就是成功
        try {
            SendResult sendResult = producerBean.send(msg);
            assert sendResult != null;
            System.out.println(sendResult);
            return sendResult.getMessageId();
        } catch (ONSClientException e) {
            log.error("send failed", e);
        }
        return null;
    }

    private void writeMessage(ProducerBean producerBean, MqConfig mqConfig) {
        while (true) {
            this.writeMessage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
