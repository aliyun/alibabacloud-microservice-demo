package com.alibabacloud.hipstershop.checkoutservice.dao;

import com.alibabacloud.hipstershop.cartserviceapi.domain.CartItem;
import com.alibabacloud.hipstershop.cartserviceapi.service.CartService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/18
 */
@Service
public class CartDao {
    @Reference(version = "1.0.0")
    private CartService cartService;

    public List<CartItem> cleanCartItems(String userId) {
        return cartService.cleanCartItems(userId);
    }
}
