package com.alibabacloud.mse.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class CApplication {

    public static void main(String[] args) {
        SpringApplication.run(CApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean(name = "serviceTag")
    String serviceTag() {
        String tag = parseServiceTag("/etc/podinfo/labels");
        if (StringUtils.isNotEmpty(tag)) {
            return tag;
        }
        return parseServiceTag("/etc/podinfo/annotations");
    }

    private String parseServiceTag(String path) {
        String tag = null;
        try {
            File file = new File(path);
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
                        } catch (Throwable ignore) {
                        }
                    }
                }
                tag = properties.getProperty("alicloud.service.tag").replace("\"", "");
            } else {
                tag = System.getProperty("alicloud.service.tag");
            }
        } catch (Throwable ignore) {
        }

        if ("null".equalsIgnoreCase(tag) || null == tag) {
            tag = "";
        }
        return tag;
    }
}
