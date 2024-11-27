package com.alibabacloud.mse.demo.c.service;

import com.alibabacloud.mse.demo.c.utils.DBExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;

import java.sql.SQLException;

@Slf4j
@DubboService(version = "1.2.0")
@RequiredArgsConstructor
public class ServiceCImpl implements ServiceC {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Autowired
    DBExecutor dbExecutor;

    @Override
    public String dubboTest1() {
        return genResult();
    }

    @Override
    public String dubboTest2() {
        return genResult();
    }

    @Override
    public String dubboTest3() {
        return genResult();
    }

    @Override
    public String dubboTest4() {
        return genResult();
    }

    @Override
    public String dubboTest5() {
        return genResult();
    }

    @Override
    public String dubboTest6() {
        return genResult();
    }

    @Override
    public String dubboTest7() {
        return genResult();
    }

    @Override
    public String dubboTest8() {
        return genResult();
    }

    @Override
    public String dubboTest9() {
        return genResult();
    }

    @Override
    public String dubboTest10() {
        return genResult();
    }

    @Override
    public String dubboTest11() {
        return genResult();
    }

    @Override
    public String dubboTest12() {
        return genResult();
    }

    @Override
    public String dubboTest13() {
        return genResult();
    }

    @Override
    public String dubboTest14() {
        return genResult();
    }

    @Override
    public String dubboTest15() {
        return genResult();
    }

    @Override
    public String dubboTest16() {
        return genResult();
    }

    @Override
    public String dubboTest17() {
        return genResult();
    }

    @Override
    public String dubboTest18() {
        return genResult();
    }

    @Override
    public String dubboTest19() {
        return genResult();
    }

    @Override
    public String dubboTest20() {
        return genResult();
    }

    @Override
    public String dubboTest21() {
        return genResult();
    }

    @Override
    public String dubboTest22() {
        return genResult();
    }

    @Override
    public String dubboTest23() {
        return genResult();
    }

    @Override
    public String dubboTest24() {
        return genResult();
    }

    @Override
    public String dubboTest25() {
        return genResult();
    }

    @Override
    public String dubboTest26() {
        return genResult();
    }

    @Override
    public String dubboTest27() {
        return genResult();
    }

    @Override
    public String dubboTest28() {
        return genResult();
    }

    @Override
    public String dubboTest29() {
        return genResult();
    }

    @Override
    public String dubboTest30() {
        return genResult();
    }

    public String genResult() {
        try {
            dbExecutor.doDBExecute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

}
