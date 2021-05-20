package com.alibabacloud.hipstershop.currencyservice.service;

import com.alibabacloud.hipstershop.currencyservice.domain.UserCurrency;
import com.alibabacloud.hipstershop.currencyserviceapi.domain.Currency;
import com.alibabacloud.hipstershop.checkoutserviceapi.service.CurrencyService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/6
 */
@DubboComponentScan
@RefreshScope
@org.springframework.stereotype.Service
@Service(version = "0.0.1")
public class CurrencyServiceImpl implements CurrencyService {


    @Value("${exception.ip:''}")
    private String exceptionIp;

    @Value("${slow.call.ip:''}")
    private String slowCallIp;

    @Value("${throwException:false}")
    private boolean throwException;

    /**
     * 临时存储货币
     */
    ConcurrentHashMap<String, Currency> currencyConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 临时存储用户默认货币类型,键为 userId,值为 UserCurrency
     */
    ConcurrentHashMap<String, UserCurrency> userCurrencyConcurrentHashMap = new ConcurrentHashMap<>();

    public CurrencyServiceImpl() {
        Currency yuan = new Currency("CNY", "China", "￥", 1.0);
        Currency dollar = new Currency("USD", "USA", "$", 7.01);
        currencyConcurrentHashMap.put("CNY", yuan);
        currencyConcurrentHashMap.put("USD", dollar);
    }

    @Override
    public List<Currency> getAllCurrency() {
        return (List<Currency>) currencyConcurrentHashMap.values();
    }

    @Override
    public Currency getCurrency(String userId) {
        if (throwException) {
            throw new RuntimeException("runtime exception");
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

        if (!userCurrencyConcurrentHashMap.containsKey(userId)) {
            userCurrencyConcurrentHashMap.put(userId, new UserCurrency(userId, "CNY"));
        }
        return currencyConcurrentHashMap.get(userCurrencyConcurrentHashMap.get(userId).getCurrencyCode());
    }

    @Override
    public Currency updateCurrency(String userId, String currencyCode) {
        userCurrencyConcurrentHashMap.put(userId, new UserCurrency(userId, currencyCode));
        return currencyConcurrentHashMap.get(currencyCode);
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
}
