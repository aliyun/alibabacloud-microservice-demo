package com.alibabacloud.hipstershop.productservice.controller;

import javax.servlet.http.HttpServletResponse;

import com.alibabacloud.hipstershop.productservice.ProductServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yizhan.xj
 */

@RestController
public class ReturnIpController {

    @ModelAttribute
    public void setVaryResponseHeader(HttpServletResponse response) {
        response.setHeader("APP_NAME", ProductServiceApplication.APP_NAME);
        response.setHeader("SERVICE_TAG", ProductServiceApplication.SERVICE_TAG);
        response.setHeader("SERVICE_IP", registration.getHost());
    }

    @Autowired
    private Registration registration;

    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getIp(@RequestParam("name") String name, @RequestParam("age") int age) {
        return registration.getHost();
    }

    @RequestMapping(value = "/getTag", method = RequestMethod.GET)
    public String getTag(@RequestParam("name") String name, @RequestParam("age") int age) {
        return ProductServiceApplication.SERVICE_TAG;
    }



    @RequestMapping(value = "/getPostTag", method = RequestMethod.POST)
    public String getPostTag(@RequestBody User user) {
        return ProductServiceApplication.SERVICE_TAG;
    }

    static  class User{
        String name;
        int age;

        public User(){}

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
