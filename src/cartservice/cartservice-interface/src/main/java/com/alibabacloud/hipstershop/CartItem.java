package com.alibabacloud.hipstershop;

import java.io.Serializable;

/**
 * @author wangtao 2019-08-14 16:35
 */
public class CartItem implements Serializable {

    public String productID;
    public int quantity;
    public String productName;
    public String productPicture;
    public int price;

    public CartItem(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
            "productID='" + productID + '\'' +
            ", quantity=" + quantity +
            ", productName='" + productName + '\'' +
            ", productPicture='" + productPicture + '\'' +
            ", price=" + price +
            '}';
    }
}
