package com.alibabacloud.hipstershop.productserviceapi.service;

import com.alibabacloud.hipstershop.productserviceapi.domain.ProductItem;

import java.util.List;

/**
 * @author xiaofeng.gxf
 * @date 2020/6/29
 */
public interface ProductService {
    /**
     * 校验库存是否足够，如果足够则扣除商品。
     * 每一个ProductItem代表一个购买的商品的库存，每个商品的校验结果用ProductItem.lock表示，lock为true代表库存足够，已扣除。
     * 目前没有添加库存属性和校验功能，走个形式，所有商品都有足够库存。
     * <p>
     * 待改进：
     * 1. 商品目前是硬编码实现，后续考虑使用数据库。
     * 2. 商品服务和库存服务分开。
     *
     * @param checkoutProductItems 订单购买的商品和数量列表
     * @return 所有商品的校验结果
     */
    List<ProductItem> confirmInventory(List<ProductItem> checkoutProductItems);
}
