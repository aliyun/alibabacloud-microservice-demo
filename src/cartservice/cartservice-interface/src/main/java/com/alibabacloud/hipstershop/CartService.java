package com.alibabacloud.hipstershop;

import java.util.List;

public interface CartService {

    List<CartItem> viewCart(String userID);

    boolean addItemToCart(String userID, String productID, int quantity);

    List<CartItem> cleanCartItems(String userID);

    String setExceptionByIp(String ip);

    String getProviderIp(String name, int age);

}