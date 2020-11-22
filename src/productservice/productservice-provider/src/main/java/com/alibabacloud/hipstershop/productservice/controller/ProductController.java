package com.alibabacloud.hipstershop.productservice.controller;

import com.alibabacloud.hipstershop.productservice.ProductServiceApplication;
import com.alibabacloud.hipstershop.productservice.entity.ProductInfo;
import com.alibabacloud.hipstershop.productservice.service.ProductServiceApi;
import com.alibabacloud.hipstershop.productserviceapi.domain.Product;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class ProductController {


    @Value("${demo_version:false}")
    private boolean demoVersion;

    @Resource
    ProductServiceApi productServiceApi;

    @Autowired
    private Registration registration;

    @ModelAttribute
    public void setVaryResponseHeader(HttpServletResponse response) {
        response.setHeader("APP_NAME", ProductServiceApplication.APP_NAME);
        response.setHeader("SERVICE_TAG", ProductServiceApplication.SERVICE_TAG);
        response.setHeader("SERVICE_IP", registration.getHost());
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable(name = "id") String id) {
        Product product =  productServiceApi.getProduct(id).getProduct();


        if(demoVersion) {
            if ("/img/products/1.png".equals(product.getPicture())) {
                product.setPicture("/img/products/air-plant.jpg");
            }
            if ("/img/products/2.png".equals(product.getPicture())) {
                product.setPicture("/img/products/barista-kit.jpg");
            }
            if ("/img/products/3.png".equals(product.getPicture())) {
                product.setPicture("/img/products/camera-lens.jpg");
            }
        }
        return product;
    }

    @PostMapping("/config")
    public String setConfig(@RequestParam("dataId") String dataId, @RequestParam("group") String group, @RequestParam("content")String content) {
        return productServiceApi.setConfig(dataId, group, content);
    }

    @PostMapping("/addFaultInstance")
    public String addFaultInstance(@RequestParam("dataId") String dataId, @RequestParam("group") String group, @RequestParam("content")String content){
        return productServiceApi.addFaultInstance(dataId, group, content);
    }

    @GetMapping("/products")
    public List<Product> getProductList() {
        List<Product> productList= productServiceApi.getAllProduct().stream().sorted(new Comparator<ProductInfo>() {
            @Override
            public int compare(ProductInfo o1, ProductInfo o2) {
                return o2.getPrice() - o1.getPrice();
            }
        }).map(ProductInfo::getProduct).collect(Collectors.toList());

        if(demoVersion){
            for(Product product : productList){
                if("/img/products/1.png".equals(product.getPicture())){
                    product.setPicture("/img/products/air-plant.jpg");
                }
                if("/img/products/2.png".equals(product.getPicture())){
                    product.setPicture("/img/products/barista-kit.jpg");
                }
                if("/img/products/3.png".equals(product.getPicture())){
                    product.setPicture("/img/products/camera-lens.jpg");
                }
            }
        }

        return productList;
    }
}
