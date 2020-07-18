package com.alibabacloud.hipstershop.productserviceapi.domain;

import java.io.Serializable;

/**
 * 购买商品项
 * <p>
 * 用于结算时检查商品库存时使用，调用方传productItem List过来，校验是否有库存不足的商品后返回校验结果。
 *
 * @author xiaofeng.gxf
 * @date 2020/6/29
 */
public class ProductItem implements Serializable {
    private String productId;
    private int quantity;
    private String orderId;
    private boolean lock;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public ProductItem() {
    }

    public ProductItem(String productId, int quantity, String orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.lock = false;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
