package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipsershop.mockapi.service.TimeoutService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timeout")
public class TimeoutController {

    @Reference(check = false, version = "1.0.0", group = "mock", timeout = 800)
    private TimeoutService timeoutService;

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String timeout1000(
            @RequestParam(value = "name", required = false, defaultValue = "timeout mock") String name){
        return timeoutService.timeout1000(name);
    }

    @RequestMapping(value = "/dynamic", method = RequestMethod.GET)
    public String timeout(
            @RequestParam(value = "name", required = false, defaultValue = "timeout mock") String name,
            @RequestParam(value = "timeout", defaultValue = "1000") int timeout) {
        return timeoutService.timeout(timeout, name);
    }
}
