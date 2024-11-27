package com.alibabacloud.mse.demo.b;

import com.alibabacloud.mse.demo.Executor;
import com.alibabacloud.mse.demo.b.service.FeignClientTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BController {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Autowired
    private FeignClientTest feignClient;

    @GetMapping("/httpTest1")
    public String httpTest1() {
        return genResult() + feignClient.feignTest1();
    }

    @GetMapping("/httpTest2")
    public String httpTest2() {
        return genResult() + feignClient.feignTest2();
    }

    @GetMapping("/httpTest3")
    public String httpTest3() {
        return genResult() + feignClient.feignTest3();
    }

    @GetMapping("/httpTest4")
    public String httpTest4() {
        return genResult() + feignClient.feignTest4();
    }

    @GetMapping("/httpTest5")
    public String httpTest5() {
        return genResult() + feignClient.feignTest5();
    }

    @GetMapping("/httpTest6")
    public String httpTest6() {
        return genResult() + feignClient.feignTest6();
    }

    @GetMapping("/httpTest7")
    public String httpTest7() {
        return genResult() + feignClient.feignTest7();
    }

    @GetMapping("/httpTest8")
    public String httpTest8() {
        return genResult() + feignClient.feignTest8();
    }

    @GetMapping("/httpTest9")
    public String httpTest9() {
        return genResult() + feignClient.feignTest9();
    }

    @GetMapping("/httpTest10")
    public String httpTest10() {
        return genResult() + feignClient.feignTest10();
    }

    @GetMapping("/httpTest11")
    public String httpTest11() {
        return genResult() + feignClient.feignTest11();
    }

    @GetMapping("/httpTest12")
    public String httpTest12() {
        return genResult() + feignClient.feignTest12();
    }

    @GetMapping("/httpTest13")
    public String httpTest13() {
        return genResult() + feignClient.feignTest13();
    }

    @GetMapping("/httpTest14")
    public String httpTest14() {
        return genResult() + feignClient.feignTest14();
    }

    @GetMapping("/httpTest15")
    public String httpTest15() {
        return genResult() + feignClient.feignTest15();
    }

    @GetMapping("/httpTest16")
    public String httpTest16() {
        return genResult() + feignClient.feignTest16();
    }

    @GetMapping("/httpTest17")
    public String httpTest17() {
        return genResult() + feignClient.feignTest17();
    }

    @GetMapping("/httpTest18")
    public String httpTest18() {
        return genResult() + feignClient.feignTest18();
    }

    @GetMapping("/httpTest19")
    public String httpTest19() {
        return genResult() + feignClient.feignTest19();
    }

    @GetMapping("/httpTest20")
    public String httpTest20() {
        return genResult() + feignClient.feignTest20();
    }

    @GetMapping("/httpTest21")
    public String httpTest21() {
        return genResult() + feignClient.feignTest21();
    }

    @GetMapping("/httpTest22")
    public String httpTest22() {
        return genResult() + feignClient.feignTest22();
    }

    @GetMapping("/httpTest23")
    public String httpTest23() {
        return genResult() + feignClient.feignTest23();
    }

    @GetMapping("/httpTest24")
    public String httpTest24() {
        return genResult() + feignClient.feignTest24();
    }

    @GetMapping("/httpTest25")
    public String httpTest25() {
        return genResult() + feignClient.feignTest25();
    }

    @GetMapping("/httpTest26")
    public String httpTest26() {
        return genResult() + feignClient.feignTest26();
    }

    @GetMapping("/httpTest27")
    public String httpTest27() {
        return genResult() + feignClient.feignTest27();
    }

    @GetMapping("/httpTest28")
    public String httpTest28() {
        return genResult() + feignClient.feignTest28();
    }

    @GetMapping("/httpTest29")
    public String httpTest29() {
        return genResult() + feignClient.feignTest29();
    }

    @GetMapping("/httpTest30")
    public String httpTest30() {
        return genResult() + feignClient.feignTest30();
    }

    @GetMapping("/httpTest31")
    public String httpTest31() {
        return genResult() + feignClient.feignTest31();
    }

    @GetMapping("/httpTest32")
    public String httpTest32() {
        return genResult() + feignClient.feignTest32();
    }

    @GetMapping("/httpTest33")
    public String httpTest33() {
        return genResult() + feignClient.feignTest33();
    }

    @GetMapping("/httpTest34")
    public String httpTest34() {
        return genResult() + feignClient.feignTest34();
    }

    @GetMapping("/httpTest35")
    public String httpTest35() {
        return genResult() + feignClient.feignTest35();
    }

    @GetMapping("/httpTest36")
    public String httpTest36() {
        return genResult() + feignClient.feignTest36();
    }

    @GetMapping("/httpTest37")
    public String httpTest37() {
        return genResult() + feignClient.feignTest37();
    }

    @GetMapping("/httpTest38")
    public String httpTest38() {
        return genResult() + feignClient.feignTest38();
    }

    @GetMapping("/httpTest39")
    public String httpTest39() {
        return genResult() + feignClient.feignTest39();
    }

    @GetMapping("/httpTest40")
    public String httpTest40() {
        return genResult() + feignClient.feignTest40();
    }

    @GetMapping("/httpTest41")
    public String httpTest41() {
        return genResult() + feignClient.feignTest41();
    }

    @GetMapping("/httpTest42")
    public String httpTest42() {
        return genResult() + feignClient.feignTest42();
    }

    @GetMapping("/httpTest43")
    public String httpTest43() {
        return genResult() + feignClient.feignTest43();
    }

    @GetMapping("/httpTest44")
    public String httpTest44() {
        return genResult() + feignClient.feignTest44();
    }

    @GetMapping("/httpTest45")
    public String httpTest45() {
        return genResult() + feignClient.feignTest45();
    }

    @GetMapping("/httpTest46")
    public String httpTest46() {
        return genResult() + feignClient.feignTest46();
    }

    @GetMapping("/httpTest47")
    public String httpTest47() {
        return genResult() + feignClient.feignTest47();
    }

    @GetMapping("/httpTest48")
    public String httpTest48() {
        return genResult() + feignClient.feignTest48();
    }

    @GetMapping("/httpTest49")
    public String httpTest49() {
        return genResult() + feignClient.feignTest49();
    }

    @GetMapping("/httpTest50")
    public String httpTest50() {
        return genResult() + feignClient.feignTest50();
    }

    public String genResult() {
        Executor.Instance().doExecute();
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> ";
    }

}
