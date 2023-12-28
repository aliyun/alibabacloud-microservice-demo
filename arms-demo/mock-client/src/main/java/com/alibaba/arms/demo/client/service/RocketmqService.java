package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.demo.client.domain.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.*;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class RocketmqService {

    private ExecutorService executors = Executors.newFixedThreadPool(20, new NamedThreadFactory("mq-service"));

    public static AtomicBoolean mqSwitch = new AtomicBoolean(true);

    public void open() {
        mqSwitch.set(true);
    }

    public void off() {
        mqSwitch.set(false);
    }

    public void sendMessage() throws ClientException, IOException {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
    }
}
