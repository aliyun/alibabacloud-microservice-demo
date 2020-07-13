package com.alibabacloud.hipstershop.currencyserviceapi.domain;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/6
 */
public class Currency {
    private String currencyCode;
    private String country;
    private String symbol;
    private Double exchangeRate;

    public Currency(String currencyCode, String country, String symbol, Double exchangeRate) {
        this.currencyCode = currencyCode;
        this.country = country;
        this.symbol = symbol;
        this.exchangeRate = exchangeRate;
    }
}
