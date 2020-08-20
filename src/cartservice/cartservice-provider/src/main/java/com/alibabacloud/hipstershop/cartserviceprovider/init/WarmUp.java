package com.alibabacloud.hipstershop.cartserviceprovider.init;

//import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibabacloud.hipstershop.cartserviceprovider.repository.RedisRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 避免第一次访问redis速度过慢，预热数据库连接
 *
 * @author xiaofeng.gxf
 * @date 2020/7/21
 */
@Component
public class WarmUp implements ApplicationRunner {

    @Resource
    RedisRepository redisRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        InitExecutor.doInit();
        redisRepository.warmUp();
    }
}
