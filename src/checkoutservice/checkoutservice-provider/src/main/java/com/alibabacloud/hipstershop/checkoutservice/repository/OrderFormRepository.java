package com.alibabacloud.hipstershop.checkoutservice.repository;

import com.alibabacloud.hipstershop.checkoutservice.entity.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
@Repository
public interface OrderFormRepository extends JpaRepository<OrderForm, String> {

}
