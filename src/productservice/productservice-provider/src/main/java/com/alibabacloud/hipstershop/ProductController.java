package com.alibabacloud.hipstershop;

import com.alibabacloud.hipstershop.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RestController
public class ProductController {

    private List<Product> products = new ArrayList<>();

    public ProductController() {
        Product p1 = new Product();
        p1.setId("OLJCESPC7Z");
        p1.setName("Vintage Typewriter");
        p1.setDescription("This typewriter looks good in your living room.");
        p1.setPicture("/img/products/typewriter.jpg");
        p1.setPrice(12);
        p1.setCategories(Arrays.asList("vintage"));
        products.add(p1);

        Product p2 = new Product();
        p2.setId("66VCHSJNUP");
        p2.setName("Vintage Good");
        p2.setDescription("You won't have a camera to use it and it probably doesn't work anyway.");
        p2.setPicture("/img/products/camera-lens.jpg");
        p2.setPrice(123);
        p2.setCategories(Arrays.asList("vintage", "photography"));
        products.add(p2);

        Product p3 = new Product();
        p3.setId("1YMWWN1N4O");
        p3.setName("Home Barista Kit");
        p3.setDescription("Always wanted to brew coffee with Chemex and Aeropress at home?");
        p3.setPicture("/img/products/barista-kit.jpg");
        p3.setPrice(500);
        p3.setCategories(Arrays.asList("cookware"));
        products.add(p3);

        Product p4 = new Product();
        p4.setId("L9ECAV7KIM");
        p4.setName("Terrarium");
        p4.setDescription("This terrarium will looks great in your white painted living room.");
        p4.setPicture("/img/products/terrarium.jpg");
        p4.setPrice(1000);
        p4.setCategories(Arrays.asList("gardening"));
        products.add(p4);

        Product p5 = new Product();
        p5.setId("2ZYFJ3GM2N");
        p5.setName("Film Camera");
        p5.setDescription("This camera looks like it's a film camera, but it's actually digital.");
        p5.setPicture("/img/products/film-camera.jpg");
        p5.setPrice(3000);
        p5.setCategories(Arrays.asList("photography", "vintage"));
        products.add(p5);

        Product p6 = new Product();
        p6.setId("0PUK6V6EV0");
        p6.setName("Vintage Record Player");
        p6.setDescription("It still works.");
        p6.setPicture("/img/products/record-player.jpg");
        p6.setPrice(800);
        p6.setCategories(Arrays.asList("music", "vintage"));
        products.add(p6);
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable(name = "id") String id) {
        for (Product p: products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        // TODO: avoid null value
        return null;
    }

    @GetMapping("/products")
    public List<Product> getProductList() {
        return Collections.unmodifiableList(products);
    }
}
