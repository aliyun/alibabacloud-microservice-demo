package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.dao.entity.DummyRecord;
import com.alibaba.arms.mock.dao.mapper.DemoMapper;
import com.alibaba.arms.mock.dao.mapper.DummyRecordMapper;
import com.alibaba.arms.mock.server.domain.AutoResetObjectWrapper;
import com.alibaba.arms.mock.server.service.trouble.IServiceTroubleInjector;
import com.aliyun.openservices.shade.org.apache.commons.lang3.math.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author aliyun
 * @date 2020/06/17
 */
@Service
@Slf4j
public class MysqlService extends AbstractComponent implements IServiceTroubleInjector,InitializingBean {


    private static final Random random = new Random();

    @Autowired
    private DemoMapper demoMapper;

    @Autowired
    private DummyRecordMapper dummyRecordMapper;

    private AutoResetObjectWrapper<String> sleepSeconds = new AutoResetObjectWrapper<>("0");

    private AutoResetObjectWrapper<Boolean> errorFlag = new AutoResetObjectWrapper<>(false);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
//            Connection connection = DriverManager.getConnection(url, user, password);
//            Statement statement = connection.createStatement();
//            boolean result = statement.execute("create database if not exists arms_mock default character set utf8mb4");
//            log.info("mysql init result:{}", result);
            int exist = dummyRecordMapper.existTable("dummy_record");
            if (exist > 0) {
                log.info("table dummy_record exist.");
            } else {
                dummyRecordMapper.createTable("dummy_record");
            }
            initInsertTable();
        } catch (Exception e) {
            log.info("mysql init error.", e);
        }
    }

    @Override
    public void injectError(Map<String, String> params) {
        this.errorFlag.update(true);
    }

    @Override
    public void cancelInjectError() {
        this.errorFlag.update(false);
    }

    @Override
    public void injectSlow(Map<String, String> params, String seconds) {
        this.sleepSeconds.update(seconds);
    }

    @Override
    public void cancelInjectSlow() {
        this.sleepSeconds.update("0");
    }

    @Override
    public String getComponentName() {
        return "mysql";
    }

    @Override
    public void execute() {
        int id = random.nextInt(1000);
        DummyRecord saved = dummyRecordMapper.selectByPrimaryKey((long) id);
        String content = String.valueOf(System.currentTimeMillis());
        if (null == saved) {
            DummyRecord dummyRecord = new DummyRecord();
            dummyRecord.setContent(content);
        }
        if (errorFlag.get()) {
            demoMapper.badSql();
            if (id % 4 == 0) {
                slowSql(content);
            }
        } else {
//            demoMapper.sleepSql(Double.valueOf(sleepSeconds.get()).toString());
            if (NumberUtils.toInt(sleepSeconds.get(), 0) > 0) {
                DummyRecord dummyRecord = new DummyRecord();
                dummyRecord.setContent(content);
                dummyRecordMapper.insert(dummyRecord);
                for (int i = 1; i <= 4; i++) {
                    slowSql(content);
                }
            }
        }
    }

    /**
     * 慢sql
     */
    public void slowSql(String content) {
        demoMapper.selectContent(content);
    }


    @Override
    public Class getImplClass() {
        return MysqlService.class;
    }
    
    private static final int COUNT_LIMIT = 10 * 10000;
    private void initInsertTable() {
        new Thread(()->{
            boolean flag = true;
            while (flag) {
                try {
                    flag = dummyRecordMapper.countTable() < COUNT_LIMIT;
                    String content = String.valueOf(System.currentTimeMillis());
                    DummyRecord dummyRecord = new DummyRecord();
                    dummyRecord.setContent(content);
                    dummyRecordMapper.insert(dummyRecord);
                } catch (Exception e) {
                    log.error("initInsertTable error.",e);
                }
            }
        }).start();
    }
}
