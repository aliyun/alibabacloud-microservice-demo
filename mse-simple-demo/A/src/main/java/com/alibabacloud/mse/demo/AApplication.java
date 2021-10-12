/*
 * Copyright (C) 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibabacloud.mse.demo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.alibabacloud.mse.demo.service.HelloServiceB;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
public class AApplication {

    public static void main(String[] args) {
        SpringApplication.run(AApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Reference(application = "${dubbo.application.id}", version = "1.0.0")
    private HelloServiceB helloServiceB;

    @RestController
    class AController {

        @Autowired
        RestTemplate restTemplate;

        @GetMapping("/a")
        public String a(HttpServletRequest request) {
            StringBuilder headerSb = new StringBuilder();
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String headerName = enumeration.nextElement();
                Enumeration<String> val = request.getHeaders(headerName);
                while (val.hasMoreElements()) {
                    String headerVal = val.nextElement();
                    headerSb.append(headerName + ":" + headerVal + ",");
                }
            }
            return "A"+SERVICE_TAG+"[" + request.getLocalAddr() + "]" + " -> " +
                restTemplate.getForObject("http://sc-B/b", String.class);
        }

        @GetMapping("/dubbo")
        public String dubbo(HttpServletRequest request) {
            StringBuilder headerSb = new StringBuilder();
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String headerName = enumeration.nextElement();
                Enumeration<String> val = request.getHeaders(headerName);
                while (val.hasMoreElements()) {
                    String headerVal = val.nextElement();
                    headerSb.append(headerName + ":" + headerVal + ",");
                }
            }
            return "A"+SERVICE_TAG+"[" + request.getLocalAddr() + "]" + " -> " +
                    helloServiceB.hello("A");
        }
    }

    public static String SERVICE_TAG="";
    static {

        try {
            File file = new File("/etc/podinfo/annotations");
            if (file.exists()) {

                Properties properties = new Properties();
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                    properties.load(fr);
                } catch (IOException e) {
                } finally {
                    if (fr != null) {
                        try {
                            fr.close();
                        } catch (Throwable ignore) {}
                    }
                }
                SERVICE_TAG = properties.getProperty("alicloud.service.tag").replace("\"","");
            }else {
                SERVICE_TAG = "";
            }
        } catch (Throwable ignore) {}

        if ("null".equalsIgnoreCase(SERVICE_TAG) || null == SERVICE_TAG) {
            SERVICE_TAG = "";
        }

    }

}
