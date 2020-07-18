package com.alibabacloud.hipstershop.productservice.entity;

import com.alibabacloud.hipstershop.productserviceapi.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
@Entity
public class ProductInfo {
    /**
     * 此处限定id长度为255是因为如果使用的mysql引擎为InnoDB时，索引长度大于191时会出错，设置字符集为UTF8时，长度最大为255
     */
    @Id
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String id;
    private String name;
    @Column(nullable = false, columnDefinition = "varchar(2000)")
    private String description;
    private String picture;
    private int price;
    @Column(nullable = false, columnDefinition = "varchar(2000)")
    private String categories;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.picture = product.getPicture();
        this.price = product.getPrice();
        StringBuffer sb = new StringBuffer();
        product.getCategories().forEach(s -> sb.append(s).append(","));
        this.categories = sb.toString();
    }

    public Product getProduct() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPicture(picture);
        product.setPrice(price);
        String[] tags = categories.split(",");
        List<String> strings = new ArrayList<>();
        for (String s : tags) {
            if (!("").equals(s)) {
                strings.add(s);
            }
        }
        product.setCategories(strings);
        return product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
