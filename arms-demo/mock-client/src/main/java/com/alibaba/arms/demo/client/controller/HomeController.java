package com.alibaba.arms.demo.client.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping( "/")
    @ResponseStatus(HttpStatus.OK)
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        List<DemoTest> list = new ArrayList<>();
        list.add(new DemoTest("应用【insights-server-0】下游依赖RT突增","/easy/http/inject_http_slow?slowSeconds=5","/easy/http/stop_inject_http_slow"));
        list.add(new DemoTest("应用【insights-server-0】下游依赖异常突增","/easy/http/inject_http_error","/easy/http/stop_inject_http_error"));
        list.add(new DemoTest("应用【insights-mysql-connection】DB连接池获取耗时突增","/easy/mysql/case/start-bad-sql?parallel=10","/easy/mysql/case/stop-bad-sql"));
        list.add(new DemoTest("应用【insights-mysql-server】数据库服务端异常","/easy/mysql/inject_mysql_slow","/easy/mysql/stop_inject_mysql_slow"));
        list.add(new DemoTest("应用【insights-redis-server】Redis热key查看","/easy/redis/case/start?parallel=500","/easy/redis/case/stop"));
//        list.add(new DemoTest("mq异常","/easy/mq/case/start","/easy/mq/case/stop"));
        model.addAttribute("myinfo",list);
        return "home";
    }
    
    @Data
    @AllArgsConstructor
    class DemoTest implements Serializable {
        private String name;
        private String startUrl;
        private String stopUrl;
    }

}
