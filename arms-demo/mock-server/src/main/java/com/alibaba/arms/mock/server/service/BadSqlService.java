package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.server.domain.AutoResetObjectWrapper;
import com.alibaba.arms.mock.server.service.trouble.IServiceTroubleInjector;
import com.aliyun.openservices.shade.org.apache.commons.lang3.RandomUtils;
import com.aliyun.openservices.shade.org.apache.commons.lang3.math.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aliyun
 * @date 2020/06/17
 */
@Service
@Slf4j
public class BadSqlService extends AbstractComponent implements IServiceTroubleInjector {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HttpService httpService;

    private AutoResetObjectWrapper<String> sleepSeconds = new AutoResetObjectWrapper<>("0");

    private AutoResetObjectWrapper<Boolean> errorFlag = new AutoResetObjectWrapper<>(false);

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
        return "bad_sql";
    }

    @Override
    public void execute() {

        jdbcTemplate.execute((ConnectionCallback<Void>) connection -> {
            Statement stmt = connection.createStatement();
            stmt.executeQuery("select 1 from dual");
            stmt.close();
            log.info("connection class={} identified hash code={}", connection.getClass(), System.identityHashCode(connection));
            httpService.executeForConnect();
            return null;
        });
    }

    @Override
    public Class getImplClass() {
        return BadSqlService.class;
    }
}
