package com.alibabacloud.hipstershop.productservice.service;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;

import com.alibabacloud.hipstershop.checkoutserviceapi.service.CurrencyService;
import com.alibabacloud.hipstershop.productservice.domain.bo.FaultInfo;
import com.alibabacloud.hipstershop.productservice.entity.ProductInfo;
import com.alibabacloud.hipstershop.productservice.repository.ProductInfoRepository;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static com.alibabacloud.hipstershop.productservice.utils.CommonUtil.getLocalIp;

/**
 * 对内的product service实现类。
 *
 * @author xiaofeng.gxf
 * @date 2020/7/8
 */
@Service
public class ProductInfoServiceImpl implements ProductServiceApi {

    @Resource
    NacosConfigManager nacosConfigManager;

    @Resource
    ProductInfoRepository productInfoRepository;

    //@Reference(version = "1.0.0", check = false)
    //private CurrencyService currencyService;

    @Override
    public ProductInfo getProduct(String id) {
        //currencyService.getAllCurrency();
        Optional<ProductInfo> productInfo = productInfoRepository.findById(id);
        return productInfo.orElse(null);
    }

    @Override
    public List<ProductInfo> getAllProduct() {
        //currencyService.getAllCurrency();
        return productInfoRepository.findAll();
    }

    @Override
    public String setConfig(String dataId, String group, String content) {
        try {
            if("clear".equals(content)){
                nacosConfigManager.getConfigService().publishConfig(dataId, group, "no exception!");
                return "config clear!";
            }
            else {
                content = content.replace('@', '\n');
                nacosConfigManager.getConfigService().publishConfig(dataId, group, content);
                return "config success!";
            }
        } catch (NacosException e) {
            e.printStackTrace();
            return "config error!";
        }
    }

    @Override
    public String addFaultInstance(String dataId, String group, String content) {
        try {
            FaultInfo faultInfo = new FaultInfo(dataId, group, content);
            if(faultInfo.isClear()){
                nacosConfigManager.getConfigService().publishConfig(dataId, group, "no exception!");
                return "";
            }
            else {
                String ip = getLocalIp();
                faultInfo.getIps().add(ip);
                nacosConfigManager.getConfigService().publishConfig(dataId, group, faultInfo.getContent());
                return String.join(",", faultInfo.getIps());
            }
        } catch (NacosException e) {
            e.printStackTrace();
            return "config error!";
        }
    }
}
