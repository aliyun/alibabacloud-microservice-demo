package com.alibabacloud.hipstershop.checkoutservice.service;

import com.alibabacloud.hipstershop.CartItem;
import com.alibabacloud.hipstershop.CartService;
import com.alibabacloud.hipstershop.checkoutserviceapi.domain.Order;
import com.alibabacloud.hipstershop.checkoutserviceapi.service.CheckoutService;
import com.alibabacloud.hipstershop.domain.ProductItem;
import com.alibabacloud.hipstershop.service.ProductService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaofeng.gxf
 * @date 2020/6/24
 */
@DubboComponentScan
@RefreshScope
@Service(version = "0.0.1")
public class CheckoutServiceImpl implements CheckoutService {

    @Reference(version = "1.0.0")
    private CartService cartService;

    @Reference(version = "1.0.0")
    private ProductService productService;

    /**
     * 临时存储订单
     */
    ConcurrentHashMap<String, Order> orderStore = new ConcurrentHashMap<>();

    @Override
    public String checkout(String email, String streetAddress, String zipCode, String city, String state,
                          String creditCardNumber, int creditCardExpirationMonth, String creditCardCvv, String userId) {
        Order order = null;
        try {
            //生成uuid
            UUID uuid = UUID.randomUUID();
            order = new Order();
            order.setOrderId(uuid.toString());
            order.setUserId(userId);

            //获取购物车商品
            List<CartItem> items = cartService.cleanCartItems(userId);
            List<ProductItem> productItems = new ArrayList<>();
            for(CartItem item : items){
                productItems.add(new ProductItem(item.getProductID(), item.getQuantity(), order.getOrderId()));
            }

            //校验库存
            List<ProductItem> lockedProductItems = productService.confirmInventory(productItems);

            //计算价格

            //校验、保存地址

            //生成订单，支付

            //运输商品

            //临时模拟
            order.setShipId("123");
            order.setProductCost(123.0);
            order.setShipCost(123.0);
            order.setTotalCost(246.0);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }

        orderStore.put(order.getOrderId(), order);

        return order.getOrderId();
    }

    @Override
    public Order getOrder(String orderId, String userId) {
        if(orderStore.containsKey(orderId)){
            Order order = orderStore.get(orderId);
            if(order.getUserId().equals(userId)){
                return order;
            }
        }
        return null;
    }
}
