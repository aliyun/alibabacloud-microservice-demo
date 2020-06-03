package com.alibabacloud.hipstershop.provider;

import com.alibabacloud.hipstershop.CartItem;
import org.apache.dubbo.config.annotation.Service;
import com.alibabacloud.hipstershop.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RefreshScope
@Service(version = "1.0.0")
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private ConcurrentHashMap<String, List<CartItem>> cartStore = new ConcurrentHashMap<>();

    @Value("${exception.ip:''}")
    private String exceptionIp;

    @Value("${slow.call.ip:''}")
    private String slowCallIp;

    @Override
    public List<CartItem> viewCart(String userID) {
        // 模拟运行时异常
        if (exceptionIp != null && exceptionIp.equals(getLocalIp())) {
            throw new RuntimeException("runtime exception");
        }
        // 模拟慢调用
        if (slowCallIp != null && slowCallIp.equals(getLocalIp())) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }

        List<CartItem> res = cartStore.getOrDefault(userID, Collections.emptyList());
        String ip = getLocalIp();
        List<CartItem> newRes = new ArrayList<>();
        if (res != null) {
            for (CartItem cartItem : res) {
                CartItem newCart = new CartItem(cartItem.productID, cartItem.quantity);
                newCart.productName = " from userId: " + userID + "; ip: " + ip;
                newCart.productPicture = cartItem.productPicture;
                newCart.price = cartItem.price;
                newRes.add(newCart);
                System.out.println(newCart);
            }
        }

        return newRes;
    }

    @Override
    public boolean addItemToCart(String userID, String productID, int quantity) {
        List<CartItem> itemList = cartStore.computeIfAbsent(userID, k -> new ArrayList<>());
        for (CartItem item : itemList) {
            if (item.productID.equals(productID)) {
                item.quantity++;
                return true;
            }
        }
        itemList.add(new CartItem(productID, quantity));
        return true;
    }

    @Override
    public String getProviderIp(String name, int age) {
        return getLocalIp();
    }

    private String getLocalIp() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            if (inetAddress != null) {
                return inetAddress.getHostAddress();//获得本机Ip;
            }
        } catch (UnknownHostException e) {
        }
        return null;
    }

    @Override
    public String setExceptionByIp(String ip) {
        String localIp = getLocalIp();
        if (localIp.equals(ip)) {
            throw new RuntimeException();
        }

        return localIp;
    }
}
