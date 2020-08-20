package com.aliware.edas;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "edas.service.consumer")
public interface EchoService {
    @RequestMapping(value = "/consumer/alive", method = RequestMethod.GET)
    boolean alive();

    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    String echo(@PathVariable("str") String str);
}
