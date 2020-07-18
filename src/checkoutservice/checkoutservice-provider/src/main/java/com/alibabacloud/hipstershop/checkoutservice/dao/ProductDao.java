package com.alibabacloud.hipstershop.checkoutservice.dao;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.alibabacloud.hipstershop.productserviceapi.domain.ProductItem;
import com.alibabacloud.hipstershop.productserviceapi.service.ProductService;

import java.util.List;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/18
 */
@Service
public class ProductDao {
    @Reference(version = "1.0.0")
    private ProductService productService;

    public List<ProductItem> confirmInventory(List<ProductItem> productItems) {
        return productService.confirmInventory(productItems);
    }
}
