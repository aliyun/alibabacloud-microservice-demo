package com.alibabacloud.hipstershop.productservice.service;



import com.alibabacloud.hipstershop.productservice.entity.ProductInfo;

import java.util.List;

/**
 * 对内的 product service 接口。
 *
 * @author xiaofeng.gxf
 * @date 2020/7/8
 */
public interface ProductServiceApi {
    /**
     * 根据商品Id查询商品
     *
     * @param id 商品Id
     * @return 对应的商品
     */
    ProductInfo getProduct(String id);

    /**
     * 获取所有商品
     *
     * @return 所有商品列表
     */
    List<ProductInfo> getAllProduct();

    /**
     * 设置config信息
     * @param dataId dataid
     * @param group group
     * @param content content
     */
    String setConfig(String dataId, String group, String content);
}
