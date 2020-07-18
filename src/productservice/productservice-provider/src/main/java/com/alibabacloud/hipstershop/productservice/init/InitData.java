package com.alibabacloud.hipstershop.productservice.init;

//import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibabacloud.hipstershop.productservice.entity.ProductInfo;
import com.alibabacloud.hipstershop.productservice.repository.ProductInfoRepository;
import com.alibabacloud.hipstershop.productserviceapi.domain.Product;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/10
 */
@Component
public class InitData implements ApplicationRunner {
    @Resource
    ProductInfoRepository productInfoRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        InitExecutor.doInit();

        List<ProductInfo> productInfoList = new ArrayList<>();
        Product p1 = new Product();
        p1.setId("OLJCESPC7Z");
        p1.setName("Air Jordan 1 Mid SE 白绿 GS");
        p1.setDescription("Air Jordan 1于1985年推出，是耐克第一双以乔丹名字命名的篮球鞋，正是这双鞋，开启了一个时代。");
        p1.setPicture("/img/products/1.png");
        p1.setPrice(1559);
        p1.setCategories(Collections.singletonList("shoe"));
        productInfoList.add(new ProductInfo(p1));

        Product p2 = new Product();
        p2.setId("66VCHSJNUP");
        p2.setName("Air Jordan Legacy 312");
        p2.setDescription("Air Jordan Legacy 312 男子运动鞋饰有醒目的设计细节，旨在向 Michael Jordan 的传奇精神致敬。匠心设计依托现代手法将经典 Jordan 元素混搭。");
        p2.setPicture("/img/products/2.png");
        p2.setPrice(1099);
        p2.setCategories(Collections.singletonList("shoe"));
        productInfoList.add(new ProductInfo(p2));

        Product p3 = new Product();
        p3.setId("1YMWWN1N4O");
        p3.setName("adidas Yezezy Boost 350");
        p3.setDescription("adidas Yeezy Boost 350 v2是迄今为止最受欢迎的Yeezy鞋之一。它采用弹性Primeknit鞋面和BOOST中底，而最醒目的细节是罗纹中底。");
        p3.setPicture("/img/products/3.png");
        p3.setPrice(1609);
        p3.setCategories(Collections.singletonList("shoe"));
        productInfoList.add(new ProductInfo(p3));

        Product p4 = new Product();
        p4.setId("L9ECAV7KIM");
        p4.setName("小飛俠阿童木");
        p4.setDescription("手冢治虫（TEZUKA CHARACTERS）- 小飛俠阿童木與菇 -彩色漫畫版。");
        p4.setPicture("/img/products/4.png");
        p4.setPrice(1000);
        p4.setCategories(Collections.singletonList("toy"));
        productInfoList.add(new ProductInfo(p4));

        Product p5 = new Product();
        p5.setId("2ZYFJ3GM2N");
        p5.setName("宠物小精灵 超梦");
        p5.setDescription("BAN DAI 万代 SHF 宠物小精灵 超梦 精灵宝可梦 Arts Remix。");
        p5.setPicture("/img/products/5.png");
        p5.setPrice(3000);
        p5.setCategories(Arrays.asList("toy", "pockmongo"));
        productInfoList.add(new ProductInfo(p5));

        Product p6 = new Product();
        p6.setId("0PUK6V6EV0");
        p6.setName("变形金刚：大黄蜂");
        p6.setDescription("threezero 《变形金刚：大黄蜂》- DLX比例合金版闪电 10.6寸。");
        p6.setPicture("/img/products/6.png");
        p6.setPrice(800);
        p6.setCategories(Collections.singletonList("toy"));
        productInfoList.add(new ProductInfo(p6));
        try {
            productInfoRepository.saveAll(productInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
