package com.alibabacloud.mse.demo;

import javax.servlet.http.HttpServletRequest;

import com.aliyuncs.mse.model.v20190531.ExportNacosConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class BApplication {

    public static void main(String[] args) {
        SpringApplication.run(BApplication.class, args);
    }


    @RestController
    static class BController {

        @GetMapping("/b")
        public String a(HttpServletRequest request) {
            return "B"+SERVICE_TAG+"[" + request.getLocalAddr() + "]" ;
        }
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
