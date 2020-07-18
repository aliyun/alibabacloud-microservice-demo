package com.alibabacloud.hipstershop.checkoutserviceapi.domain;

import com.alibabacloud.hipstershop.productserviceapi.domain.ProductItem;

import java.io.Serializable;
import java.util.List;

/**
 * 订单对象，用于通信
 *
 * @author xiaofeng.gxf
 * @date 2020/6/24
 */
public class Order implements Serializable {
    private String orderId;
    private String shipId;
    private String userId;
    private Double shipCost;
    private Double productCost;
    private Double totalCost;
    private List<ProductItem> productItemList;
    private Integer status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getShipCost() {
        return shipCost;
    }

    public void setShipCost(Double shipCost) {
        this.shipCost = shipCost;
    }

    public Double getProductCost() {
        return productCost;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public List<ProductItem> getProductItemList() {
        return productItemList;
    }

    public void setProductItemList(List<ProductItem> productItemList) {
        this.productItemList = productItemList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Order() {
    }

    public Order(String orderId, String shipId, String userId, Double shipCost, Double productCost, Double totalCost, List<ProductItem> productItemList, Integer status) {
        this.orderId = orderId;
        this.shipId = shipId;
        this.userId = userId;
        this.shipCost = shipCost;
        this.productCost = productCost;
        this.totalCost = totalCost;
        this.productItemList = productItemList;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", shipId='" + shipId + '\'' +
                ", userId='" + userId + '\'' +
                ", shipCost=" + shipCost +
                ", productCost=" + productCost +
                ", totalCost=" + totalCost +
                '}';
    }
}
