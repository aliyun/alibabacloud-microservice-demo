package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.dao.CartDAO;
import com.alibabacloud.hipstershop.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yizhan.xj
 */

@RestController
@RequestMapping("/router")
public class CanaryTestController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CartDAO cartDAO;

    @RequestMapping(value = "/dubbo", method = RequestMethod.GET)
    public String dubbo(
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        return cartDAO.getRemoteIp(name, age);
    }

    @RequestMapping(value = "/springcloud", method = RequestMethod.GET)
    public String springcloud(
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        return productDAO.getRemoteIp(name, age);
    }

}
