package com.alibabacloud.mse.demo.b.service;

import com.alibabacloud.mse.demo.Executor;
import com.alibabacloud.mse.demo.c.service.ServiceC;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import java.util.Random;

@Service(version = "1.2.0")
public class ServiceBImpl implements ServiceB {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
    private ServiceC serviceC;

    @Override
    public String dubboTest1() {
        return genResult() + serviceC.dubboTest1();
    }

    @Override
    public String dubboTest2() {
        return genResult() + serviceC.dubboTest2();
    }

    @Override
    public String dubboTest3() {
        return genResult() + serviceC.dubboTest3();
    }

    @Override
    public String dubboTest4() {
        return genResult() + serviceC.dubboTest4();
    }

    @Override
    public String dubboTest5() {
        return genResult() + serviceC.dubboTest5();
    }

    @Override
    public String dubboTest6() {
        return genResult() + serviceC.dubboTest6();
    }

    @Override
    public String dubboTest7() {
        return genResult() + serviceC.dubboTest7();
    }

    @Override
    public String dubboTest8() {
        return genResult() + serviceC.dubboTest8();
    }

    @Override
    public String dubboTest9() {
        return genResult() + serviceC.dubboTest9();
    }

    @Override
    public String dubboTest10() {
        return genResult() + serviceC.dubboTest10();
    }

    @Override
    public String dubboTest11() {
        return genResult() + serviceC.dubboTest11();
    }

    @Override
    public String dubboTest12() {
        return genResult() + serviceC.dubboTest12();
    }

    @Override
    public String dubboTest13() {
        return genResult() + serviceC.dubboTest13();
    }

    @Override
    public String dubboTest14() {
        return genResult() + serviceC.dubboTest14();
    }

    @Override
    public String dubboTest15() {
        return genResult() + serviceC.dubboTest15();
    }

    @Override
    public String dubboTest16() {
        return genResult() + serviceC.dubboTest16();
    }

    @Override
    public String dubboTest17() {
        return genResult() + serviceC.dubboTest17();
    }

    @Override
    public String dubboTest18() {
        return genResult() + serviceC.dubboTest18();
    }

    @Override
    public String dubboTest19() {
        return genResult() + serviceC.dubboTest19();
    }

    @Override
    public String dubboTest20() {
        return genResult() + serviceC.dubboTest20();
    }

    @Override
    public String dubboTest21() {
        return genResult() + serviceC.dubboTest21();
    }

    @Override
    public String dubboTest22() {
        return genResult() + serviceC.dubboTest22();
    }

    @Override
    public String dubboTest23() {
        return genResult() + serviceC.dubboTest23();
    }

    @Override
    public String dubboTest24() {
        return genResult() + serviceC.dubboTest24();
    }

    @Override
    public String dubboTest25() {
        return genResult() + serviceC.dubboTest25();
    }

    @Override
    public String dubboTest26() {
        return genResult() + serviceC.dubboTest26();
    }

    @Override
    public String dubboTest27() {
        return genResult() + serviceC.dubboTest27();
    }

    @Override
    public String dubboTest28() {
        return genResult() + serviceC.dubboTest28();
    }

    @Override
    public String dubboTest29() {
        return genResult() + serviceC.dubboTest29();
    }

    @Override
    public String dubboTest30() {
        return genResult() + serviceC.dubboTest30();
    }

    public String genResult() {
        Executor.Instance().doExecute();
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> ";
    }
}
