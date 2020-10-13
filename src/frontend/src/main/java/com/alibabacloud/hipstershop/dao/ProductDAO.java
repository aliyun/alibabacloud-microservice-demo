package com.alibabacloud.hipstershop.dao;

import com.alibabacloud.hipstershop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.alibabacloud.hipstershop.web.AppController.PRODUCT_APP_NAME;
import static com.alibabacloud.hipstershop.web.AppController.PRODUCT_SERVICE_TAG;
import static com.alibabacloud.hipstershop.web.AppController.PRODUCT_IP;

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

        ResponseEntity<List<Product>> responseEntity = productService.getProductList();

        PRODUCT_APP_NAME = getValueFromHttpHeader(responseEntity.getHeaders(),"APP_NAME");
        PRODUCT_SERVICE_TAG = getValueFromHttpHeader(responseEntity.getHeaders(),"SERVICE_TAG");
        PRODUCT_IP = getValueFromHttpHeader(responseEntity.getHeaders(),"SERVICE_IP");

        return responseEntity.getBody();
    }

    public String addFaultInstance(String dataId, String group, String content){
        return productService.addFaultInstance(dataId, group, content);
    }

    public String getRemoteIp(String name, int age) {
        return restTemplate.getForObject("http://productservice/getIp?name=" + name + "&age=" + age, String.class);
    }

    public String getRemoteTag(String name, int age) {
        return restTemplate.getForObject("http://productservice/getTag?name=" + name + "&age=" + age, String.class);
    }

    public String postRequestBody(String name, int age) {

        String json ="{\"name\": \""+name+"\",\"age\": "+age+"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
            new HttpEntity<String>(json, headers);

        return restTemplate.postForObject("http://productservice/getPostTag",request, String.class);
    }

    public String setExceptionByIp(String ip) {
        return restTemplate.getForObject("http://productservice/setExceptionByIp?ip=" + ip, String.class);
    }


    @FeignClient(name = "productservice")
    public interface ProductService {

        @GetMapping("/products/")
        ResponseEntity<List<Product>> getProductList();

        @GetMapping("/product/{id}")
        Product getProductById(@PathVariable(name = "id") String id);

        @PostMapping("/addFaultInstance")
        public String addFaultInstance(@RequestParam("dataId") String dataId, @RequestParam("group") String group, @RequestParam("content")String content);
    }

    private String getValueFromHttpHeader(HttpHeaders httpHeaders, String key) {
        return null == httpHeaders.get(key) ? "" : httpHeaders.get(key).get(0);
    }
}
