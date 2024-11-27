package com.alibabacloud.mse.demo.c;

import com.alibabacloud.mse.demo.c.utils.DBExecutor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
class CController {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Autowired
    DBExecutor dbExecutor;

    @GetMapping("/httpTest1")
    public String httpTest1() {
        return genResult();
    }

    @GetMapping("/httpTest2")
    public String httpTest2() {
        return genResult();
    }

    @GetMapping("/httpTest3")
    public String httpTest3() {
        return genResult();
    }

    @GetMapping("/httpTest4")
    public String httpTest4() {
        return genResult();
    }

    @GetMapping("/httpTest5")
    public String httpTest5() {
        return genResult();
    }

    @GetMapping("/httpTest6")
    public String httpTest6() {
        return genResult();
    }

    @GetMapping("/httpTest7")
    public String httpTest7() {
        return genResult();
    }

    @GetMapping("/httpTest8")
    public String httpTest8() {
        return genResult();
    }

    @GetMapping("/httpTest9")
    public String httpTest9() {
        return genResult();
    }

    @GetMapping("/httpTest10")
    public String httpTest10() {
        return genResult();
    }

    @GetMapping("/httpTest11")
    public String httpTest11() {
        return genResult();
    }

    @GetMapping("/httpTest12")
    public String httpTest12() {
        return genResult();
    }

    @GetMapping("/httpTest13")
    public String httpTest13() {
        return genResult();
    }

    @GetMapping("/httpTest14")
    public String httpTest14() {
        return genResult();
    }

    @GetMapping("/httpTest15")
    public String httpTest15() {
        return genResult();
    }

    @GetMapping("/httpTest16")
    public String httpTest16() {
        return genResult();
    }

    @GetMapping("/httpTest17")
    public String httpTest17() {
        return genResult();
    }

    @GetMapping("/httpTest18")
    public String httpTest18() {
        return genResult();
    }

    @GetMapping("/httpTest19")
    public String httpTest19() {
        return genResult();
    }

    @GetMapping("/httpTest20")
    public String httpTest20() {
        return genResult();
    }

    @GetMapping("/httpTest21")
    public String httpTest21() {
        return genResult();
    }

    @GetMapping("/httpTest22")
    public String httpTest22() {
        return genResult();
    }

    @GetMapping("/httpTest23")
    public String httpTest23() {
        return genResult();
    }

    @GetMapping("/httpTest24")
    public String httpTest24() {
        return genResult();
    }

    @GetMapping("/httpTest25")
    public String httpTest25() {
        return genResult();
    }

    @GetMapping("/httpTest26")
    public String httpTest26() {
        return genResult();
    }

    @GetMapping("/httpTest27")
    public String httpTest27() {
        return genResult();
    }

    @GetMapping("/httpTest28")
    public String httpTest28() {
        return genResult();
    }

    @GetMapping("/httpTest29")
    public String httpTest29() {
        return genResult();
    }

    @GetMapping("/httpTest30")
    public String httpTest30() {
        return genResult();
    }

    @GetMapping("/httpTest31")
    public String httpTest31() {
        return genResult();
    }

    @GetMapping("/httpTest32")
    public String httpTest32() {
        return genResult();
    }

    @GetMapping("/httpTest33")
    public String httpTest33() {
        return genResult();
    }

    @GetMapping("/httpTest34")
    public String httpTest34() {
        return genResult();
    }

    @GetMapping("/httpTest35")
    public String httpTest35() {
        return genResult();
    }

    @GetMapping("/httpTest36")
    public String httpTest36() {
        return genResult();
    }

    @GetMapping("/httpTest37")
    public String httpTest37() {
        return genResult();
    }

    @GetMapping("/httpTest38")
    public String httpTest38() {
        return genResult();
    }

    @GetMapping("/httpTest39")
    public String httpTest39() {
        return genResult();
    }

    @GetMapping("/httpTest40")
    public String httpTest40() {
        return genResult();
    }

    @GetMapping("/httpTest41")
    public String httpTest41() {
        return genResult();
    }

    @GetMapping("/httpTest42")
    public String httpTest42() {
        return genResult();
    }

    @GetMapping("/httpTest43")
    public String httpTest43() {
        return genResult();
    }

    @GetMapping("/httpTest44")
    public String httpTest44() {
        return genResult();
    }

    @GetMapping("/httpTest45")
    public String httpTest45() {
        return genResult();
    }

    @GetMapping("/httpTest46")
    public String httpTest46() {
        return genResult();
    }

    @GetMapping("/httpTest47")
    public String httpTest47() {
        return genResult();
    }

    @GetMapping("/httpTest48")
    public String httpTest48() {
        return genResult();
    }

    @GetMapping("/httpTest49")
    public String httpTest49() {
        return genResult();
    }

    @GetMapping("/httpTest50")
    public String httpTest50() {
        return genResult();
    }

    public String genResult(){
        try {
            dbExecutor.doDBExecute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }
}
