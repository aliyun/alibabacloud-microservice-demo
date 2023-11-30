package com.alibabacloud.mse.demo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static String SERVICE_TAG="";
    static {

        try {
            File file = new File("/etc/podinfo/labels");
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
