package com.alibabacloud.hipstershop.cartserviceapi.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wangtao 2019-08-14 16:35
 */
public class CartItem implements Serializable {
    private String productID;
    private int quantity;
    private String productName;
    private String productPicture;
    private int price;

    public CartItem() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item = (CartItem) o;
        return productID.equals(item.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }
}
