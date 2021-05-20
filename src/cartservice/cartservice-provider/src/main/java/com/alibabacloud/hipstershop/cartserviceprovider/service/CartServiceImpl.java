package com.alibabacloud.hipstershop.cartserviceprovider.service;

import com.alibabacloud.hipstershop.cartserviceapi.domain.CartItem;
import com.alibabacloud.hipstershop.cartserviceprovider.repository.RedisRepository;
import com.alibabacloud.hipstershop.checkoutserviceapi.service.CurrencyService;
import com.alibabacloud.hipstershop.currencyserviceapi.domain.Currency;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import com.alibabacloud.hipstershop.cartserviceapi.service.CartService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

//@RefreshScope
@Service(version = "1.0.0")
@org.springframework.stereotype.Service
@EnableDubbo
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Resource
    private RedisRepository redisRepository;

    @Reference(version = "0.0.1", check = false)
    private CurrencyService currencyService;

    @Value("${exception.ip:''}")
    private String exceptionIp;

    @Value("${slow.call.ip:''}")
    private String slowCallIp;

    @Value("${throwException:false}")
    private boolean throwException;

    @Override
    public List<CartItem> viewCart(String userId) {

        if(throwException){
            throw new RuntimeException();
        }

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

        Currency currency = currencyService.getCurrency(userId);
        Double rate = currency.getExchangeRate();
        List<CartItem> res = redisRepository.getUserCartItems(userId);
        String ip = getLocalIp();
        List<CartItem> newRes = new ArrayList<>();
        if (res != null) {
            for (CartItem cartItem : res) {
                CartItem newCart = new CartItem(cartItem.getProductID(), cartItem.getQuantity());
                newCart.setProductName(" from userId: " + userId + "; ip: " + ip);
                newCart.setProductPicture(cartItem.getProductPicture());
                newCart.setPrice(cartItem.getPrice());
                newRes.add(newCart);
//                System.out.println(newCart);
            }
        }
        return newRes;
    }

    @Override
    public boolean addItemToCart(String userId, String productId, int quantity) {
        return redisRepository.save(new CartItem(productId, quantity), userId);
    }

    @Override
    public List<CartItem> cleanCartItems(String userId) {
        List<CartItem> items = viewCart(userId);
        redisRepository.removeUserCartItems(userId);
        return items;
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
                //获得本机Ip;
                return inetAddress.getHostAddress();
            }
        } catch (UnknownHostException ignored) {
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
