package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.server.service.rocketmq.MessageWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author aliyun
 * @date 2021/06/10
 */
@Service
@Slf4j
public class RocketMQWriteService extends AbstractComponent {

    @Autowired(required = false)
    private MessageWriter messageWriter;

    @Override
    public String getComponentName() {
        return "rocketMQWrite";
    }

    @Override
    public void execute() {
        messageWriter.writeMessage();
    }

    @Override
    public Class getImplClass() {
        return RocketMQWriteService.class;
    }

}
