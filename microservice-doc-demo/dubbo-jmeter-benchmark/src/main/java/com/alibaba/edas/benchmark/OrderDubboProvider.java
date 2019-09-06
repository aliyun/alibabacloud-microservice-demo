package com.alibaba.edas.benchmark;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderDubboProvider implements OrderApi {

    @Autowired
    OrderService orderService;

    @Override
    public OrderDTO queryOrder(long orderNo) {
        return orderService.queryOrder(orderNo);
    }
}
