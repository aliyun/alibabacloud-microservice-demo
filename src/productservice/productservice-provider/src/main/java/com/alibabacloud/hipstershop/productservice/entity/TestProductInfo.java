package com.alibabacloud.hipstershop.productservice.entity;

import com.alibabacloud.hipstershop.productserviceapi.domain.Product;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/29
 */
public class TestProductInfo extends ProductInfo{

    @Override
    public Product getProduct() {
        Product product = new Product();
        return product;
    }
}
