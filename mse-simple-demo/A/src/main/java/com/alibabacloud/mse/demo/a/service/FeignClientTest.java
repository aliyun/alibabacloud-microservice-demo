package com.alibabacloud.mse.demo.a.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yushan
 * @date 2023年02月21日
 */
//这是springcloud  RPCFeignClient的调用方式
@Component
@FeignClient(name = "sc-B")
public interface FeignClientTest {

    @GetMapping("/bByFeign")
    String bByFeign(@RequestParam("s") String s);

    @GetMapping("/circuit-breaker-rt-b")
    String circuit_breaker_rt_b();

    @GetMapping("/circuit-breaker-exception-b")
    String circuit_breaker_exception_b();
}
