package com.alibabacloud.mse.demo.b.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yushan
 * @date 2023年02月21日
 */
//这是springcloud  RPCFeignClient的调用方式
@Component
@FeignClient(name = "sc-C", fallbackFactory = FeignClientFallback.class)
public interface FeignClientTest {
    @GetMapping("/httpTest1")
    String feignTest1();

    @GetMapping("/httpTest2")
    String feignTest2();

    @GetMapping("/httpTest3")
    String feignTest3();

    @GetMapping("/httpTest4")
    String feignTest4();

    @GetMapping("/httpTest5")
    String feignTest5();

    @GetMapping("/httpTest6")
    String feignTest6();

    @GetMapping("/httpTest7")
    String feignTest7();

    @GetMapping("/httpTest8")
    String feignTest8();

    @GetMapping("/httpTest9")
    String feignTest9();

    @GetMapping("/httpTest10")
    String feignTest10();

    @GetMapping("/httpTest11")
    String feignTest11();

    @GetMapping("/httpTest12")
    String feignTest12();

    @GetMapping("/httpTest13")
    String feignTest13();

    @GetMapping("/httpTest14")
    String feignTest14();

    @GetMapping("/httpTest15")
    String feignTest15();

    @GetMapping("/httpTest16")
    String feignTest16();

    @GetMapping("/httpTest17")
    String feignTest17();

    @GetMapping("/httpTest18")
    String feignTest18();

    @GetMapping("/httpTest19")
    String feignTest19();

    @GetMapping("/httpTest20")
    String feignTest20();

    @GetMapping("/httpTest21")
    String feignTest21();

    @GetMapping("/httpTest22")
    String feignTest22();

    @GetMapping("/httpTest23")
    String feignTest23();

    @GetMapping("/httpTest24")
    String feignTest24();

    @GetMapping("/httpTest25")
    String feignTest25();

    @GetMapping("/httpTest26")
    String feignTest26();

    @GetMapping("/httpTest27")
    String feignTest27();

    @GetMapping("/httpTest28")
    String feignTest28();

    @GetMapping("/httpTest29")
    String feignTest29();

    @GetMapping("/httpTest30")
    String feignTest30();

    @GetMapping("/httpTest31")
    String feignTest31();

    @GetMapping("/httpTest32")
    String feignTest32();

    @GetMapping("/httpTest33")
    String feignTest33();

    @GetMapping("/httpTest34")
    String feignTest34();

    @GetMapping("/httpTest35")
    String feignTest35();

    @GetMapping("/httpTest36")
    String feignTest36();

    @GetMapping("/httpTest37")
    String feignTest37();

    @GetMapping("/httpTest38")
    String feignTest38();

    @GetMapping("/httpTest39")
    String feignTest39();

    @GetMapping("/httpTest40")
    String feignTest40();

    @GetMapping("/httpTest41")
    String feignTest41();

    @GetMapping("/httpTest42")
    String feignTest42();

    @GetMapping("/httpTest43")
    String feignTest43();

    @GetMapping("/httpTest44")
    String feignTest44();

    @GetMapping("/httpTest45")
    String feignTest45();

    @GetMapping("/httpTest46")
    String feignTest46();

    @GetMapping("/httpTest47")
    String feignTest47();

    @GetMapping("/httpTest48")
    String feignTest48();

    @GetMapping("/httpTest49")
    String feignTest49();

    @GetMapping("/httpTest50")
    String feignTest50();
}
