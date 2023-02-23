package com.alibabacloud.mse.demo.a.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yushan
 * @date 2023年02月21日
 */
@org.springframework.cloud.openfeign.FeignClient("sc-B")
public interface FeignClient {

    @GetMapping("/bByFeign")
    String bByFeign(@RequestParam("s") String s);
}
