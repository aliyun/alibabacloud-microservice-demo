package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.server.domain.AutoResetObjectWrapper;
import com.alibaba.arms.mock.server.service.trouble.IServiceTroubleInjector;
import com.aliyun.openservices.shade.org.apache.commons.lang3.RandomUtils;
import com.aliyun.openservices.shade.org.apache.commons.lang3.StringUtils;
import com.aliyun.openservices.shade.org.apache.commons.lang3.math.NumberUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author aliyun
 * @date 2020/06/17
 */

@Service
@Slf4j
public class RedisService extends AbstractComponent implements IServiceTroubleInjector {

    private static final String INVALID_SLOW = "0";

    private static final String DEFAULT_BIG_SIZE = "2";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private AutoResetObjectWrapper<Boolean> injectErrorFlag = new AutoResetObjectWrapper<>(false);
    private AutoResetObjectWrapper<String> slowFlag = new AutoResetObjectWrapper<>(INVALID_SLOW);
    private AutoResetObjectWrapper<Boolean> injectBigFlag = new AutoResetObjectWrapper<>(false);
    private AutoResetObjectWrapper<String> sizeFlag = new AutoResetObjectWrapper<>(DEFAULT_BIG_SIZE);

    private static final ExecutorService es = Executors.newFixedThreadPool(1000);


    @Override
    public void injectError(Map<String, String> params) {
        injectErrorFlag.update(true);
    }

    @Override
    public void cancelInjectError() {
        injectErrorFlag.update(false);
    }

    @Override
    public void injectSlow(Map<String, String> params, String seconds) {
        if (Strings.isNullOrEmpty(seconds)) {
            return;
        }
        slowFlag.update(seconds);
    }

    @Override
    public void injectBig(Map<String, String> params, String size) {
        injectBigFlag.update(true);
        sizeFlag.update(size);
    }

    @Override
    public void cancelInjectBig() {
        injectBigFlag.update(false);
    }

    @Override
    public void cancelInjectSlow() {
        slowFlag.update(INVALID_SLOW);
    }

    @Override
    public String getComponentName() {
        return "redis";
    }

    @Override
    public void execute() {


        redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String key = "hot_key";
                String bigKey = "big_key";
                redisConnection.set(key.getBytes(), "world".getBytes());
                if (injectErrorFlag.get()) {
                    redisConnection.hGet(key.getBytes(), "bad".getBytes());
                }
//                String slowSeconds = slowFlag.get();
                long len = redisConnection.lLen(bigKey.getBytes());
                if (len < 120000) {
                    redisConnection.lPush(bigKey.getBytes(),"aiops_slow_key".getBytes());
                }
                for (int i = 0; i < 10;i++) {
                    es.submit(new Runnable() {
                        @Override
                        public void run() {
                            redisConnection.get(key.getBytes());
                        }
                    });
                }
//                if (!Strings.isNullOrEmpty(slowSeconds) && !INVALID_SLOW.equalsIgnoreCase(slowSeconds)) {
//                    Double sleepS = Double.valueOf(slowSeconds);
//                    redisConnection.bLPop(Double.valueOf(sleepS * 1000).intValue(), "aiops_slow_key".getBytes());
//                }
//                if (injectBigFlag.get()) {
//                    redisConnection.set(bigKey.getBytes(), getBigString().getBytes());
//                    List<Character> list = new ArrayList<>(100000);
//                    Collections.fill(list,'0');
//                    redisConnection.lSet("big_list".getBytes(), 0, StringUtils.join(list.toArray(),",").getBytes());
//                    for (int i = 0; i < 200;i++) {
//                        es.submit(new Runnable() {
//                            @Override
//                            public void run() {
//                                redisConnection.get(key.getBytes());
//                            }
//                        });
//                    }
//                }
                redisConnection.get(key.getBytes());
                return "redis-ok";
            }
        });
    }



    private String getBigString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5 * 1024 * 1024; i++) {
            stringBuilder.append(RandomUtils.nextInt(0, 10));
        }
        return stringBuilder.toString();
    }

    @Override
    public Class getImplClass() {
        return RedisService.class;
    }


}
