package com.alibabacloud.hipstershop;

import java.io.Serializable;

/**
 * @author wangtao 2019-08-14 16:35
 */
public class CartItem implements Serializable {
    private String productID;
    private int quantity;
    private String productName;
    private String productPicture;
    private int price;

    public CartItem(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
