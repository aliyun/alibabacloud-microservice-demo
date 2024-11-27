package com.alibabacloud.mse.demo.a;

import com.alibabacloud.mse.demo.Executor;
import com.alibabacloud.mse.demo.a.service.FeignClientTest;
import com.alibabacloud.mse.demo.b.service.ServiceB;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
class AController {

    @Autowired
    private FeignClientTest feignClient;

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
    private ServiceB serviceB;

    private static Random random = new Random();


    @GetMapping("/httpTest")
    public String httpTest() {
        Executor.Instance().doExecute();
        String i;
        i = String.valueOf(random.nextInt(50) + 1);
        String result = "";
        switch (i) {
            case "1":
                result = feignClient.feignTest1();
                break;
            case "2":
                result = feignClient.feignTest2();
                break;

            case "3":
                result = feignClient.feignTest3();
                break;
            case "4":
                result = feignClient.feignTest4();
                break;
            case "5":
                result = feignClient.feignTest5();
                break;
            case "6":
                result = feignClient.feignTest6();
                break;
            case "7":
                result = feignClient.feignTest7();
                break;
            case "8":
                result = feignClient.feignTest8();
                break;
            case "9":
                result = feignClient.feignTest9();
                break;
            case "10":
                result = feignClient.feignTest10();
                break;
            case "11":
                result = feignClient.feignTest11();
                break;
            case "12":
                result = feignClient.feignTest12();
                break;
            case "13":
                result = feignClient.feignTest13();
                break;
            case "14":
                result = feignClient.feignTest14();
                break;
            case "15":
                result = feignClient.feignTest15();
                break;
            case "16":
                result = feignClient.feignTest16();
                break;
            case "17":
                result = feignClient.feignTest17();
                break;
            case "18":
                result = feignClient.feignTest18();
                break;
            case "19":
                result = feignClient.feignTest19();
                break;
            case "20":
                result = feignClient.feignTest20();
                break;
            case "21":
                result = feignClient.feignTest21();
                break;
            case "22":
                result = feignClient.feignTest22();
                break;
            case "23":
                result = feignClient.feignTest23();
                break;
            case "24":
                result = feignClient.feignTest24();
                break;
            case "25":
                result = feignClient.feignTest25();
                break;
            case "26":
                result = feignClient.feignTest26();
                break;
            case "27":
                result = feignClient.feignTest27();
                break;
            case "28":
                result = feignClient.feignTest28();
                break;
            case "29":
                result = feignClient.feignTest29();
                break;
            case "30":
                result = feignClient.feignTest30();
                break;
            case "31":
                result = feignClient.feignTest31();
                break;
            case "32":
                result = feignClient.feignTest32();
                break;
            case "33":
                result = feignClient.feignTest33();
                break;
            case "34":
                result = feignClient.feignTest34();
                break;
            case "35":
                result = feignClient.feignTest35();
                break;
            case "36":
                result = feignClient.feignTest36();
                break;
            case "37":
                result = feignClient.feignTest37();
                break;
            case "38":
                result = feignClient.feignTest38();
                break;
            case "39":
                result = feignClient.feignTest39();
                break;
            case "40":
                result = feignClient.feignTest40();
                break;
            case "41":
                result = feignClient.feignTest41();
                break;
            case "42":
                result = feignClient.feignTest42();
                break;
            case "43":
                result = feignClient.feignTest43();
                break;
            case "44":
                result = feignClient.feignTest44();
                break;
            case "45":
                result = feignClient.feignTest45();
                break;
            case "46":
                result = feignClient.feignTest46();
                break;
            case "47":
                result = feignClient.feignTest47();
                break;
            case "48":
                result = feignClient.feignTest48();
                break;
            case "49":
                result = feignClient.feignTest49();
                break;
            case "50":
                result = feignClient.feignTest50();
                break;
            default:
                break;
        }
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                result;
    }


    @GetMapping("/dubboTest")
    public String dubbo() {
        Executor.Instance().doExecute();
        String i;
        i = String.valueOf(random.nextInt(30) + 1);
        String result = "";
        switch (i) {
            case "1":
                result = serviceB.dubboTest1();
                break;
            case "2":
                result = serviceB.dubboTest2();
                break;
            case "3":
                result = serviceB.dubboTest3();
                break;
            case "4":
                result = serviceB.dubboTest4();
                break;
            case "5":
                result = serviceB.dubboTest5();
                break;
            case "6":
                result = serviceB.dubboTest6();
                break;
            case "7":
                result = serviceB.dubboTest7();
                break;
            case "8":
                result = serviceB.dubboTest8();
                break;
            case "9":
                result = serviceB.dubboTest9();
                break;
            case "10":
                result = serviceB.dubboTest10();
                break;
            case "11":
                result = serviceB.dubboTest11();
                break;
            case "12":
                result = serviceB.dubboTest12();
                break;
            case "13":
                result = serviceB.dubboTest13();
                break;
            case "14":
                result = serviceB.dubboTest14();
                break;
            case "15":
                result = serviceB.dubboTest15();
                break;
            case "16":
                result = serviceB.dubboTest16();
                break;
            case "17":
                result = serviceB.dubboTest17();
                break;
            case "18":
                result = serviceB.dubboTest18();
                break;
            case "19":
                result = serviceB.dubboTest19();
                break;
            case "20":
                result = serviceB.dubboTest20();
                break;
            case "21":
                result = serviceB.dubboTest21();
                break;
            case "22":
                result = serviceB.dubboTest22();
                break;
            case "23":
                result = serviceB.dubboTest23();
                break;
            case "24":
                result = serviceB.dubboTest24();
                break;
            case "25":
                result = serviceB.dubboTest25();
                break;
            case "26":
                result = serviceB.dubboTest26();
                break;
            case "27":
                result = serviceB.dubboTest27();
                break;
            case "28":
                result = serviceB.dubboTest28();
                break;
            case "29":
                result = serviceB.dubboTest29();
                break;
            case "30":
                result = serviceB.dubboTest30();
                break;
            default:
                break;
        }
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                result;
    }

}
