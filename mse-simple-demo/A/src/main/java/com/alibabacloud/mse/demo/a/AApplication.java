
package com.alibabacloud.mse.demo.a;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@SpringBootApplication
@EnableFeignClients
public class AApplication {

    public static void main(String[] args) {
        SpringApplication.run(AApplication.class, args);
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
