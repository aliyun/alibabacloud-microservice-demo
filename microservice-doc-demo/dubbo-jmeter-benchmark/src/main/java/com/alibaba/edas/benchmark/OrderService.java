package com.alibaba.edas.benchmark;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class OrderService {

    public OrderDTO queryOrder(long orderNo) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo(orderNo);
        orderDTO.setTotalPrice(new BigDecimal(ThreadLocalRandom.current().nextDouble(100.0D)));
        orderDTO.setBody(new byte[1000]);
        return orderDTO;
    }
}
