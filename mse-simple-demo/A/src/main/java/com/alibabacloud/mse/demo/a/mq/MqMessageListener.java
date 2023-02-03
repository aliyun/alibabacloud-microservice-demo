package com.alibabacloud.mse.demo.a.mq;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

@Slf4j
@Component
public class MqMessageListener implements MessageListener {

    private RestTemplate restTemplate;

    private InetUtils inetUtils;

    private String serviceTag;

    public MqMessageListener(RestTemplate restTemplate, InetUtils inetUtils, String servcieTag) {
        this.restTemplate = restTemplate;
        this.inetUtils = inetUtils;
        this.serviceTag = servcieTag;
    }

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        try {
            String topic = message.getTopic();
            String tags = message.getTag();
            String messageString = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("topic:{},tags:{},messageString:{},no result yet", topic, tags, messageString);
            String result = "A" + StringUtils.stripToEmpty(serviceTag) + "["
                    + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]"
                    + " -> " +
                    restTemplate.getForObject("http://sc-B/b", String.class);

            log.info("topic:{},tags:{},messageString:{},result:{}", topic, tags, messageString, result);

            return Action.CommitMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return Action.CommitMessage;
        }
    }
}