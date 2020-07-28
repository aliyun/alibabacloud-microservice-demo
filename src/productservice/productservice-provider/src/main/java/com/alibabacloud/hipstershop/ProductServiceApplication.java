package com.alibabacloud.hipstershop;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }


    public static String SERVICE_TAG;
    public static String SERVICE_IP;
    public static String APP_NAME = System.getProperty("msc.appName","nil");

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
                SERVICE_TAG = "nil";
            }
        } catch (Throwable ignore) {}
    }
}
