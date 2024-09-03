package com.alibabacloud.mse.demo.a;

import com.alibaba.fastjson.JSON;
import com.alibabacloud.mse.demo.a.service.FeignClientTest;
import com.alibabacloud.mse.demo.b.service.HelloServiceB;
import com.alibabacloud.mse.demo.b.service.HelloServiceBTwo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Api(value = "/", tags = {"入口应用"})
@RestController
@RefreshScope
class AController {
    private static final Logger log = LoggerFactory.getLogger(AController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeignClientTest feignClient;

    @Autowired
    InetUtils inetUtils;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0", group = "DEFAULT_GROUP")
    private HelloServiceB helloServiceB;

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
            //在阿里云中判断在哪个地区的内网服务地址，如杭州会输出cn-hangzhou-g
            HttpGet req = new HttpGet("http://100.100.100.200/latest/meta-data/zone-id");
            req.setConfig(requestConfig);
            HttpResponse response = client.execute(req);
            currentZone = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            currentZone = e.getMessage();
        }
    }

    @ApiOperation(value = "HTTP 全链路灰度入口", tags = {"入口应用"})
    @RequestMapping("/a")
    public String a() throws ExecutionException, InterruptedException {
        //这是rpc调用的方式
        String result = restTemplate.getForObject("http://sc-B/b", String.class);

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @ApiOperation(value = "HTTP 全链路灰度入口 a调用b和c", tags = {"入口应用"})
    @GetMapping("/a2bc")
    public String a2bc() throws ExecutionException, InterruptedException {

        String resultB = "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + restTemplate.getForObject("http://sc-B/b", String.class);
        String resultA = "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + restTemplate.getForObject("http://sc-C/c", String.class);

        return resultA + "\n" + resultB;
    }

    @ApiOperation(value = "HTTP 全链路灰度入口 feign", tags = {"入口应用"})
    @GetMapping("/aByFeign")
    public String aByFeign() throws ExecutionException, InterruptedException {

        String result = feignClient.bByFeign("test");

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @ApiOperation(value = "测试防护规则" , tags = {"流量防护"})
    @GetMapping("/flow")
    public String flow() throws ExecutionException, InterruptedException {

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://sc-B/flow", String.class);
        HttpStatus status = responseEntity.getStatusCode();
        String result = responseEntity.getBody() + " code:" + status.value();

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }


    @ApiOperation(value = "测试热点规则", tags = {"流量防护"})
    @GetMapping("/params/{hot}")
    public String params(@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://sc-B/params/" + hot, String.class);

        HttpStatus status = responseEntity.getStatusCode();
        String result = responseEntity.getBody() + " code:" + status.value();

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " params:" + hot + " -> " + result;
    }

    @ApiOperation(value = "测试隔离规则", tags = { "流量防护"})
    @GetMapping("/isolate")
    public String isolate() throws ExecutionException, InterruptedException {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://sc-B/isolate", String.class);

        HttpStatus status = responseEntity.getStatusCode();
        String result = responseEntity.getBody() + " code:" + status.value();

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @GetMapping("/sql")
    public String sql(@RequestParam Map<String, String> allRequestParams) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder("http://sc-B/sql?");
        String enc = "UTF-8";
        for (Map.Entry<String, String> entry : allRequestParams.entrySet()) {
            url.append(URLEncoder.encode(entry.getKey(), enc));
            url.append("=");
            url.append(URLEncoder.encode(entry.getValue(), enc));
            url.append("&");
        }
        String result = restTemplate.getForObject(url.toString(), String.class);
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @ApiOperation(value = "HTTP 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/a-zone")
    public String aZone() {
        return "A[tag=" + serviceTag + "][" + currentZone + "]" + " -> " +
                restTemplate.getForObject("http://sc-B/b-zone", String.class);
    }

    @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/dubbo")
    public String dubbo(@RequestParam(required = false) String param) {
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello(param);
    }


    @ApiOperation(value = "Dubbo 全链路灰度入口", tags = {"入口应用"})
    @GetMapping("/dubbo2")
    public String dubbo2(@RequestParam Map<String,String> allRequestParams) {
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceBTwo.hello2(JSON.toJSONString(allRequestParams));
    }

    @ApiOperation(value = "Dubbo 限流测试", tags = {"入口应用"})
    @GetMapping("/dubbo-flow")
    public String dubbo_flow() {
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello("A");
    }

    @ApiOperation(value = "Dubbo 热点测试", tags = {"入口应用"})
    @GetMapping("/dubbo-params/{hot}")
    public String dubbo_params(@PathVariable("hot") String hot) {
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " params:" + hot + " -> " +
                helloServiceB.hello(hot);
    }

    @ApiOperation(value = "Dubbo 隔离测试", tags = {"入口应用"})
    @GetMapping("/dubbo-isolate")
    public String dubbo_isolate() {
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.hello("isolate");
    }

    @ApiOperation(value = "熔断 rt 测试", tags = {"流量防护"})
    @GetMapping("/circuit-breaker-rt")
    public String circuit_breaker_rt() throws ExecutionException, InterruptedException {

        String result = feignClient.circuit_breaker_rt_b();

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @ApiOperation(value = "熔断异常测试" , tags = {"流量防护"})
    @GetMapping("/circuit-breaker-exception")
    public String circuit_breaker_exception() throws ExecutionException, InterruptedException {
        String result = feignClient.circuit_breaker_exception_b();

        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" +
                "[config=" + configValue + "]" + " -> " + result;
    }

    @GetMapping("swagger-demo")
    @ApiOperation(value = "这是一个演示swagger的接口 ", tags = {"首页操作页面"})
    public String swagger(@ApiParam(name = "name", value = "我是姓名", required = true) String name,
                          @ApiParam(name = "age", value = "我是年龄", required = true) int age,
                          @ApiParam(name = "aliware-products", value = "我是购买阿里云原生产品列表", required = true) List<String> aliwareProducts) {
        return "hello swagger";
    }

    @ApiOperation(value = "Dubbo rt 熔断测试", tags = {"入口应用"})
    @GetMapping("/dubbo-circuit-breaker-rt")
    public String dubbo_circuit_breaker_rt() {
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceB.slow();
    }

    @ApiOperation(value = "Dubbo 异常熔断测试", tags = {"入口应用"})
    @GetMapping("/dubbo-circuit-breaker-exception")
    public String dubbo_circuit_breaker_exception() {
        String response = "";
        try {
            response = helloServiceB.exception();
        } catch (Exception ex) {
            if (ex.getClass().getName().contains("com.alibaba.csp.sentinel")) {
                response = "Service is in abnormal status now, and block by mse circuit rule!";
            } else {
                response = "Service is in abnormal status and throws exception by itself!";
            }
        }
        return "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + response;
    }

    @ApiOperation(value = "Nacos灰度配置", tags = {"入口应用"})
    @RequestMapping("/custom-config")
    public String getCustomConfig() throws ExecutionException, InterruptedException {
        return this.configValue;
    }
}
