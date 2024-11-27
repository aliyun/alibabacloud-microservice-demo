package com.alibabacloud.mse.demo.c.jedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class JedisConfiguration {

    @Bean
    public Jedis getJedis() {
        return new Jedis("127.0.0.1", 6379);
    }
}
