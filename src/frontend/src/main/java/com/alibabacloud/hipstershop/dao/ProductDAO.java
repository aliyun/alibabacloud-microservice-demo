package com.alibabacloud.hipstershop.dao;

import com.alibabacloud.hipstershop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangtao 2019-08-12 17:24
 */
@Service
public class ProductDAO {

    public Product getProductById(String id) {
        Product p1 = new Product();
        p1.setId("1");
        p1.setName("Vintage Typewriter");
        p1.setDescription("xxx");
        p1.setPicture("/img/products/typewriter.jpg");
        p1.setPrice(12);
        p1.setCategories(Arrays.asList("vintage"));
        return p1;
    }

    public List<Product> getProductList() {
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId("1");
        p1.setName("Vintage Typewriter");
        p1.setDescription("xxx");
        p1.setPicture("/img/products/typewriter.jpg");
        p1.setPrice(12);
        p1.setCategories(Arrays.asList("vintage"));
        products.add(p1);

        Product p2 = new Product();
        p2.setId("2");
        p2.setName("Vintage Good");
        p2.setDescription("bbb");
        p2.setPicture("/img/products/camera-lens.jpg");
        p2.setPrice(123);
        p2.setCategories(Arrays.asList("vintage", "photography"));
        products.add(p2);
        return products;
    }
}
