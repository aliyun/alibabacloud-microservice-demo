package com.alibabacloud.hipstershop.repository;

import com.alibabacloud.hipstershop.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

}
