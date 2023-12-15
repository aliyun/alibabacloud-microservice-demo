//package com.alibaba.arms.mock.server.controller;
//
//import com.alibaba.arms.mock.api.Invocation;
//import com.alibaba.arms.mock.server.service.ComponentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author aliyun
// * @date 2021/01/19
// */
//@RestController
//@RequestMapping("/components/api/v1")
//public class RocketMQController {
//
//    @Autowired
//    private ComponentService componentService;
//
//    @RequestMapping(value = "/rocketmq/success", method = RequestMethod.POST)
//    public String success(@RequestBody Invocation invocation, @RequestParam("level") int level) {
//        return componentService.execute(invocation, level);
//    }
//
//    @RequestMapping(value = "/rocketmq/error", method = RequestMethod.POST)
//    public String error(@RequestBody Invocation invocation, @RequestParam("level") int level) {
//        return componentService.execute(invocation, level);
//    }
//
//    @RequestMapping(value = "/rocketmq/slow", method = RequestMethod.POST)
//    public String slow(@RequestBody Invocation invocation, @RequestParam("level") int level) {
//        return componentService.execute(invocation, level);
//    }
//
//    @RequestMapping(value = "/rocketmq/timeout", method = RequestMethod.POST)
//    public String timeout(@RequestBody Invocation invocation, @RequestParam("level") int level) {
//        return componentService.execute(invocation, level);
//    }
//
//    @RequestMapping(value = "/rocketmq/stupid", method = RequestMethod.POST)
//    public String stupid(@RequestBody Invocation invocation, @RequestParam("level") int level) {
//        return componentService.execute(invocation, level);
//    }
//}
