package com.alibabacloud.mse.demo.a;

import com.alibabacloud.mse.demo.b.service.HelloServiceB;
import com.alibabacloud.mse.demo.b.service.HelloServiceBTwo;
import com.alibabacloud.mse.demo.c.service.HelloServiceC;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/dubbo")
class DubboController {
    private static final Logger log = LoggerFactory.getLogger(DubboController.class);

    @Autowired
    InetUtils inetUtils;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0", group = "DEFAULT_GROUP")
    private HelloServiceB helloServiceB;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0", group = "DEFAULT_GROUP")
    private HelloServiceC helloServiceC;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0", group = "DEFAULT_GROUP")
    private HelloServiceBTwo helloServiceBTwo;

    @Autowired
    String serviceTag;

    @Value("${custom.config.value}")
    private String configValue;

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
            // 在阿里云中判断在哪个地区的内网服务地址，如杭州会输出cn-hangzhou-g
            HttpGet req = new HttpGet("http://100.100.100.200/latest/meta-data/zone-id");
            req.setConfig(requestConfig);
            HttpResponse response = client.execute(req);
            currentZone = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            currentZone = e.getMessage();
        }
    }

    @GetMapping("a2bc")
    public String a2bc(@RequestParam(required = false) String param) {
        String resultB = "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello(param);
        String resultC = "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.hello(param);
        return resultB + "\n" + resultC;
    }

    @GetMapping("/dubbo")
    public String dubbo(@RequestParam(required = false) String param) {
        return "A" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello(param);
    }
}
