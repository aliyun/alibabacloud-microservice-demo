package com.alibabacloud.hipstershop.checkoutservice.entity;

import com.alibabacloud.hipstershop.checkoutservice.utils.JsonUtils;
import com.alibabacloud.hipstershop.checkoutserviceapi.domain.Order;
import com.alibabacloud.hipstershop.productserviceapi.domain.ProductItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Pojo,对应于数据库表
 *
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
@Entity
public class OrderForm {
    @Id
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String orderId;
    private String shipId;
    private String userId;
    private Double shipCost;
    private Double productCost;
    private Double totalCost;
    private Integer status;
    @Column(nullable = false, columnDefinition = "varchar(2000)")
    private String productList;

    public OrderForm() {
    }

    public OrderForm(Order order) {
        this.orderId = order.getOrderId();
        this.shipId = order.getShipId();
        this.userId = order.getUserId();
        this.shipCost = order.getShipCost();
        this.productCost = order.getProductCost();
        this.totalCost = order.getTotalCost();
        this.status = order.getStatus();
        try {
            this.productList = JsonUtils.objectMapper.writeValueAsString(order.getProductItemList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            this.productList = "";
        }
    }

    /**
     * 将OrderForm转化为Order对象
     *
     * @return order对象
     */
    public Order getOrder() {
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setShipId(this.shipId);
        order.setUserId(this.userId);
        order.setShipCost(this.shipCost);
        order.setProductCost(this.productCost);
        order.setTotalCost(this.totalCost);
        order.setStatus(this.status);
        try {
            order.setProductItemList(JsonUtils.objectMapper.readValue(this.productList,
                    new TypeReference<List<ProductItem>>() {
                    }));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            order.setProductItemList(null);
        }
        return order;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }
}
