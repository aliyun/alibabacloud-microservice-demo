package com.alibaba.edas.benchmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/queryOrder/{orderNo}")
    public OrderDTO queryOrder(@PathVariable("orderNo") long orderNo) {
        return orderService.queryOrder(orderNo);
    }

}
