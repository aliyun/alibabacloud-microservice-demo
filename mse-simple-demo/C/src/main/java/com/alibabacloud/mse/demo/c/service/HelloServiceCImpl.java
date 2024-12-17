package com.alibabacloud.mse.demo.c.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@DubboService(version = "1.2.0", group = "DEFAULT_GROUP")
@RequiredArgsConstructor
public class HelloServiceCImpl implements HelloServiceC {

    private final ClientServiceProvider provider = ClientServiceProvider.loadService();

    private final Producer producer;

    @Autowired
    InetUtils inetUtils;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    @Override
    public String hello(String name) {

        if (throwException) {
            throw new RuntimeException();
        }

        String value = "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " param:" + name;
        String invokerTag="";
        String userData = RpcContext.getContext().getAttachment("__microservice_tag__");
        if (!isEmpty(userData)) {
            invokerTag = substringBefore(userData,",").split("\"")[3];
        }

        try {
            final Message message = provider.newMessageBuilder()
                    // Set topic for the current message.
                    .setTopic(topic)
                    .setBody(value.getBytes(StandardCharsets.UTF_8))
                    .build();

            producer.send(message);
        } catch (Exception e) {
            log.error("error:", e);
        }

        return value;
    }

    @Override
    public String world(String name) {
        return  "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "] -> " + name;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String substringBefore(String str, String separator) {
        if (!isEmpty(str) && separator != null) {
            if (separator.isEmpty()) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1 ? str : str.substring(0, pos);
            }
        } else {
            return str;
        }
    }

}
