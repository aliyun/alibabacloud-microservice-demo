package com.alibabacloud.hipstershop.checkoutserviceapi.service;

import com.alibabacloud.hipstershop.currencyserviceapi.domain.Currency;

import java.util.List;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/6
 */
public interface CurrencyService {
    /**
     * 获取目前所有的货币种类
     * @return currency对象组成的list。
     */
    List<Currency> getAllCurrency();

    /**
     * 获得用户默认货币选项
     * @param userId 用户Id
     * @return 用户默认货币种类
     */
    Currency getCurrency(String userId);

    /**
     * 更新用户默认货币选项
     * @param userId 用户Id
     * @param currencyCode 货币标志码
     * @return 更换之后的货币种类
     */
    Currency updateCurrency(String userId, String currencyCode);
}
