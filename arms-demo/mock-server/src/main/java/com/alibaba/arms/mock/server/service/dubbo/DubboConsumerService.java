package com.alibaba.arms.mock.server.service.dubbo;

/**
 * @author aliyun
 * @date 2021/08/02
 */


import com.alibaba.arms.mock.api.IGreeting;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author aliyun
 * @date 2021/07/22
 */
@Slf4j
@Service
public class DubboConsumerService {
    @DubboReference(version = "1.0.0", check = false, timeout = 5000, injvm = false, retries = 0)
    private IGreeting greeting;

    public void execute() {
        log.info(greeting.sayHi("mercyblitz"));
    }
}
