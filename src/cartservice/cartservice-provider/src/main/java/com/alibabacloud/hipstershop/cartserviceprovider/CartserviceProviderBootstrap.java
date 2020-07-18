package com.alibabacloud.hipstershop.cartserviceprovider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class CartserviceProviderBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CartserviceProviderBootstrap.class)
                .run(args);
    }
}