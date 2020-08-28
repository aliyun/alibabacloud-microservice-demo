package com.alibabacloud.hipstershop.productservice.service;

import com.alibabacloud.hipstershop.productservice.utils.Constant;
import com.alibabacloud.hipstershop.productserviceapi.domain.ProductItem;
import com.alibabacloud.hipstershop.productserviceapi.service.ProductService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.*;

import static com.alibabacloud.hipstershop.productservice.utils.CommonUtil.getLocalIp;

/**
 * 对外提供的Dubbo service实现类
 *
 * @author xiaofeng.gxf
 * @date 2020/6/29
 */

@DubboComponentScan
@RefreshScope
@Service(version = "1.0.0")
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${exception.ips:}")
    private String exceptionIps;

    @Value("${exception.rate: 0}")
    private Integer rate;

    @Value("${exception.type:}")
    private String exceptionType;

    @Value("${exception.value: 0}")
    private Integer exceptionValue;

    @Override
    public List<ProductItem> confirmInventory(List<ProductItem> checkoutProductItems) {
        if(!"".equals(exceptionIps) && !"".equals(exceptionType)){
            String ip = getLocalIp();
            Set<String> ips = new HashSet<>(Arrays.asList(exceptionIps.split(",")));
            if(ips.contains(ip)) {
                Random random = new Random();
                int i = random.nextInt(100);
                if (i < rate) {
                    if(Constant.SLEEP.equals(exceptionType)) {
                        try {
                            Thread.sleep(exceptionValue);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(Constant.FULL_GC.equals(exceptionType)) {
                        List<Object> list = new ArrayList<>();
                        for(int j = 0; j < exceptionValue; j++) {
                            list.add(new byte[10 * 1024 * 1024]);
                            System.gc();
                        }
                        list = null;
                    }
                }
            }
        }
        for (ProductItem item : checkoutProductItems) {
            //@TODO check inventory
            item.setLock(true);
        }
        return checkoutProductItems;
    }
}
