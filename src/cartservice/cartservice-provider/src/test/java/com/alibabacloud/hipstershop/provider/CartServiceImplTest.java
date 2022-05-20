package com.alibabacloud.hipstershop.provider;

import com.alibabacloud.hipstershop.cartserviceprovider.service.CartServiceImpl;
import org.junit.Test;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

public class CartServiceImplTest {

    @Test
    public void testGetLocalIp() {
        CartServiceImpl cartService = new CartServiceImpl();
        try {
            Method method = CartServiceImpl.class.getDeclaredMethod("getLocalIp");
            method.setAccessible(true);
            Assert.notNull(method.invoke(cartService), "the return value shouldn't be null");
        } catch (Exception e) {
        }
    }
}
