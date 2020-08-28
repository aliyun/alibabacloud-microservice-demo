package com.alibabacloud.hipstershop.dao;

import com.alibabacloud.hipstershop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductDAO {

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    public Product getProductById(String id) {
        return productService.getProductById(id);
    }

    public List<Product> getProductList() {
        return productService.getProductList();
    }

    public String addFaultInstance(String dataId, String group, String content){
        return productService.addFaultInstance(dataId, group, content);
    }

    public String getRemoteIp(String name, int age) {
        return restTemplate.getForObject("http://productservice/getIp?name=" + name + "&age=" + age, String.class);
    }

    public String setExceptionByIp(String ip) {
        return restTemplate.getForObject("http://productservice/setExceptionByIp?ip=" + ip, String.class);
    }


    @FeignClient(name = "productservice")
    public interface ProductService {

        @GetMapping("/products/")
        List<Product> getProductList();

        @GetMapping("/product/{id}")
        Product getProductById(@PathVariable(name = "id") String id);

        @PostMapping("/addFaultInstance")
        public String addFaultInstance(@RequestParam("dataId") String dataId, @RequestParam("group") String group, @RequestParam("content")String content);
    }
}
