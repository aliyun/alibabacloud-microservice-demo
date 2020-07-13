package com.alibabacloud.hipstershop.service;

import com.alibabacloud.hipstershop.domain.ProductItem;
import com.alibabacloud.hipstershop.entity.ProductInfo;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 对外提供的Dubbo service实现类
 * @author xiaofeng.gxf
 * @date 2020/6/29
 */

@DubboComponentScan
@RefreshScope
@Service(version = "1.0.0")
public class ProductServiceImpl implements ProductService{

    @Override
    public List<ProductItem> confirmInventory(List<ProductItem> checkoutProductItems) {
        for(ProductItem item: checkoutProductItems){
            //@TODO check inventory
            item.setLock(true);
        }
        return checkoutProductItems;
    }
}
