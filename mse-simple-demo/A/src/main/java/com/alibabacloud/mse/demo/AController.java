package com.alibabacloud.mse.demo;

import com.alibabacloud.mse.demo.service.HelloServiceB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Tag(name = "入口应用")
@RestController
class AController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    InetUtils inetUtils;

    @Reference(application = "${dubbo.application.id}", version = "1.1.0")
    private HelloServiceB helloServiceB;

    @Autowired
    String servcieTag;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    private String currentZone;

    @PostConstruct
    private void init() {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)
                    .setConnectTimeout(1000)
                    .setSocketTimeout(1000)
                    .build();
            HttpGet req = new HttpGet("http://100.100.100.200/latest/meta-data/zone-id");
            req.setConfig(requestConfig);
            HttpResponse response = client.execute(req);
            currentZone = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            currentZone = e.getMessage();
        }
    }

    @Operation(summary = "HTTP 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/a")
    public String a(HttpServletRequest request) throws ExecutionException, InterruptedException {
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

        String result = restTemplate.getForObject("http://sc-B/b", String.class);
//        String result = taskExecutor.submit(() ->
//                restTemplate.getForObject("http://sc-B/b", String.class)
//        ).get();

        return "A" + servcieTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                result;
    }

    @Operation(summary = "HTTP 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/a-zone")
    public String aZone(HttpServletRequest request) {
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
        return "A" + servcieTag + "[" + currentZone + "]" + " -> " +
                restTemplate.getForObject("http://sc-B/b-zone", String.class);
    }

    @Operation(summary = "Dubbo 全链路灰度入口", tags = {"入口应用"})
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
        return "A" + servcieTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello("A");
    }

    @GetMapping("swagger-demo")
    @Operation(summary = "这是一个演示swagger的接口 ", tags = {"首页操作页面"})
    public String swagger(
            @Parameter(name = "name", description = "我是姓名", required = true) String name,
            @Parameter(name = "age", description = "我是年龄", required = true) int age,
            @Parameter(name = "aliware-products", description = "我是购买阿里云原生产品列表", required = true) List<String> aliwareProducts
    ) {
        return "hello swagger";
    }
}
