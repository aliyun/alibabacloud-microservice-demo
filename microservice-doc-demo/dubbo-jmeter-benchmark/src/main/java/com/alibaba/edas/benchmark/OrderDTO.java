package com.alibaba.edas.benchmark;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDTO implements Serializable {

    private long orderNo;
    private BigDecimal totalPrice;
    private byte[] body;

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
