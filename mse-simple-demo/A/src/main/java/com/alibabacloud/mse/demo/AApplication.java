
package com.alibabacloud.mse.demo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.alibabacloud.mse.demo.service.HelloServiceB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.util.InetUtils;
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

    @Api(value = "/", tags = {"入口应用"})
    @RestController
    class AController {

        @Autowired
        RestTemplate restTemplate;

        @Autowired
        InetUtils inetUtils;

        @ApiOperation(value = "HTTP 全链路灰度入口", tags = {"入口应用"})
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
            return "A"+SERVICE_TAG+"[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                restTemplate.getForObject("http://sc-B/b", String.class);
        }

        @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
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
            return "A"+SERVICE_TAG+"[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                    helloServiceB.hello("A");
        }

        @GetMapping("swagger-demo")
        @ApiOperation(value = "这是一个演示swagger的接口 ", tags = {"首页操作页面"})
        public String swagger(@ApiParam(name = "name", value = "我是姓名", required = true) String name,
                              @ApiParam(name = "age", value = "我是年龄", required = true)int age,
                              @ApiParam(name = "aliware-products", value = "我是购买阿里云原生产品列表", required = true) List<String> aliwareProducts) {
            return "hello swagger";
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
            } else {
                SERVICE_TAG = System.getProperty("alicloud.service.tag");
            }
        } catch (Throwable ignore) {}

        if ("null".equalsIgnoreCase(SERVICE_TAG) || null == SERVICE_TAG) {
            SERVICE_TAG = "";
        }

    }

}
