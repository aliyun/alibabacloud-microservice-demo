package com.alibabacloud.hipstershop.checkoutserviceapi.service;

import com.alibabacloud.hipstershop.checkoutserviceapi.domain.Order;

/**
 * @author xiaofeng.gxf
 * @date 2020/6/24
 */
public interface CheckoutService {
    /**
     * 结算接口，用于生成订单。
     * 主要逻辑如下：
     * 1. 调用cartservice的cleanCartItems接口，获取用户的购物车商品同时清空购物车。
     * <p>
     * 2. 调用productservice的confirmInventory，确认所选商品库存是否足够。（目前商品没有库存属性，所以认为商品库存无限。）
     * <p>
     * 3. 调用currencyservice的calculatePrice，计算商品当前货币的总价。（目前货币默认只有人民币，所以会直接返回总价。）
     * <p>
     * 其中每一步如果失败需要回滚。
     *
     * @param email                     用户邮箱（接收shipping信息。）
     * @param streetAddress             详细地址（商品邮寄地址）
     * @param zipCode                   邮政编码
     * @param city                      城市
     * @param state                     州（省）
     * @param creditCardNumber          信用卡号
     * @param creditCardExpirationMonth 信用卡过期月份
     * @param creditCardCvv             信用卡验证码
     * @param userId                    用户Id
     * @return 返回orderId
     * @TODO 待改进点
     * <p>
     * 1. 完善productservice的库存和currencyservice的其余货币逻辑。
     * <p>
     * 2. 添加分布式事务。
     * <p>
     * 3. 订单设定过期时间（redis实现）。
     */
    String checkout(String email, String streetAddress, String zipCode, String city, String state, String country,
                    String creditCardNumber, int creditCardExpirationMonth, String creditCardCvv, String userId);

    /**
     * 根据userId和orderId获取订单
     *
     * @param orderId 订单Id
     * @param userId  用户Id
     * @return 订单对象
     */
    Order getOrder(String orderId, String userId);
}
