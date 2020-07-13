package com.alibabacloud.hipstershop.currencyservice.service;

import com.alibabacloud.hipstershop.currencyservice.domain.UserCurrency;
import com.alibabacloud.hipstershop.currencyserviceapi.domain.Currency;
import com.alibabacloud.hipstershop.checkoutserviceapi.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/6
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    /**
     * 临时存储货币
     */
    ConcurrentHashMap<String, Currency> currencyConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 临时存储用户默认货币类型,键为 userId,值为 UserCurrency
     */
    ConcurrentHashMap<String, UserCurrency> userCurrencyConcurrentHashMap = new ConcurrentHashMap<>();

    public CurrencyServiceImpl(){
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
        if(!userCurrencyConcurrentHashMap.containsKey(userId)){
            userCurrencyConcurrentHashMap.put(userId, new UserCurrency(userId, "CNY"));
        }
        return currencyConcurrentHashMap.get(userCurrencyConcurrentHashMap.get(userId).getCurrencyCode());
    }

    @Override
    public Currency updateCurrency(String userId, String currencyCode) {
        userCurrencyConcurrentHashMap.put(userId, new UserCurrency(userId, currencyCode));
        return currencyConcurrentHashMap.get(currencyCode);
    }
}
