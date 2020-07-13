package com.alibabacloud.hipstershop.service;

import com.alibabacloud.hipstershop.entity.ProductInfo;
import com.alibabacloud.hipstershop.repository.ProductInfoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 对内的product service实现类。
 * @author xiaofeng.gxf
 * @date 2020/7/8
 */
@Service
public class ProductInfoServiceImpl implements ProductServiceApi{

    @Resource
    ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo getProduct(String id) {
        Optional<ProductInfo> productInfo = productInfoRepository.findById(id);
        return productInfo.orElse(null);
    }

    @Override
    public List<ProductInfo> getAllProduct() {
        return productInfoRepository.findAll();
    }
}
