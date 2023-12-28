package com.alibaba.arms.mock.server.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.*;
import org.apache.rocketmq.client.apis.consumer.*;
import org.apache.rocketmq.client.apis.message.MessageId;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class RocketMqService {
    @Autowired
    MysqlService mysqlService;
    
    @PostConstruct
    public void receiveMsg() throws ClientException, IOException {
    }
}
