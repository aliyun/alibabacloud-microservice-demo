package com.alibabacloud.hipstershop.provider;

import com.alibabacloud.hipstershop.CartItem;
import org.apache.dubbo.config.annotation.Service;
import com.alibabacloud.hipstershop.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service(version = "1.0.0")
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private ConcurrentHashMap<String, List<CartItem>> cartStore = new ConcurrentHashMap<>();

    @Override
    public List<CartItem> viewCart(String userID) {
        return cartStore.getOrDefault(userID, Collections.emptyList());
    }

    @Override
    public boolean addItemToCart(String userID, String productID, int quantity) {
        List<CartItem> itemList = cartStore.computeIfAbsent(userID, k -> new ArrayList<>());
        for (CartItem item: itemList) {
            if (item.productID.equals(productID)) {
                item.quantity++;
                return true;
            }
        }
        itemList.add(new CartItem(productID, quantity));
        return true;
    }
}
