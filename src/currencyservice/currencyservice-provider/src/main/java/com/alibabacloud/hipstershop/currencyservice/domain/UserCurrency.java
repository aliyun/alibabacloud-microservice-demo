package com.alibabacloud.hipstershop.currencyservice.domain;

/**
 *
 * 用户默认货币
 * 后续改进为持久化存储
 *
 * @author xiaofeng.gxf
 * @date 2020/7/6
 */
public class UserCurrency {
    private String userId;
    private String currencyCode;

    public UserCurrency(String userId, String currencyCode) {
        this.userId = userId;
        this.currencyCode = currencyCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
