package com.alibabacloud.mse.demo.b;

import com.alibaba.fastjson.JSON;
import com.alibabacloud.mse.demo.c.service.HelloServiceC;
import com.alibabacloud.mse.demo.common.TrafficAttribute;
import com.alibabacloud.mse.demo.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
class BController {

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate loadBalancedRestTemplate;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
    private HelloServiceC helloServiceC;

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    private String currentZone;

    private static final Random RANDOM = new Random();

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


    @GetMapping("/flow")
    public String flow() throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        String result = loadBalancedRestTemplate.getForObject("http://sc-C/flow", String.class);
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime + " -> " + result;
    }

    @GetMapping("/params/{hot}")
    public String params(@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        String result = loadBalancedRestTemplate.getForObject("http://sc-C/params/" + hot, String.class);
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime + " params:" + hot + " -> " + result;
    }

    @GetMapping("/isolate")
    public String isolate() throws ExecutionException, InterruptedException {
        long sleepTime = 500 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        String result = loadBalancedRestTemplate.getForObject("http://sc-C/isolate", String.class);
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " sleepTime:" + sleepTime + " -> " + result;
    }

    @GetMapping("/b")
    public String b() {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                loadBalancedRestTemplate.getForObject("http://sc-C/c", String.class);
    }

    @GetMapping("/bByFeign")
    public String bByFeign(String s) {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

    @GetMapping("/circuit-breaker-rt-b")
    public String circuit_breaker_rt_b() {

        Integer rt = TrafficAttribute.getInstance().getRt();
        Integer ration = TrafficAttribute.getInstance().getSlowRation();

        boolean isSlowRequest = RANDOM.nextInt(100) < ration ? true : false;
        if (isSlowRequest) {
            silentSleep(rt);
        }

        String slowMessage = isSlowRequest ? " RT:" + rt : "";

        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + slowMessage;
    }

    @GetMapping("/circuit-breaker-exception-b")
    public String circuit_breaker_exception_b() {
        Integer ration = TrafficAttribute.getInstance().getExceptionRation();
        boolean isExceptionRequest = RANDOM.nextInt(100) < ration ? true : false;
        if (isExceptionRequest) {
            throw new RuntimeException("TestCircuitBreakerException");
        }
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

    @GetMapping("/b-zone")
    public String bZone() {
        return "B" + serviceTag + "[" + currentZone + "]" + " -> " +
                loadBalancedRestTemplate.getForObject("http://sc-C/c-zone", String.class);
    }

    @GetMapping("/spring_boot")
    public String spring_boot() {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                restTemplate.getForObject("http://sc-c:20003/spring_boot", String.class);
    }

    @GetMapping("/sql")
    public String sql(@RequestParam Map<String, String> allRequestParams) {
        User user = new User();
        String command = allRequestParams.get("command");
        String result = "";
        switch (command) {
            case "query":
                user.setId(Long.parseLong(allRequestParams.get("id")));
                result = JSON.toJSONString(user.selectById());
                break;
            case "insert":
                user.setName(allRequestParams.get("name"));
                user.setAge(Integer.parseInt(allRequestParams.get("age")));
                user.setEmail(allRequestParams.get("email"));
                result = String.valueOf(user.insert());
                break;
            case "delete":
                user.setId(Long.parseLong(allRequestParams.get("id")));
                result = String.valueOf(user.deleteById());
                break;
            case "update":
                user.setId(Long.parseLong(allRequestParams.get("id")));
                user.setName(allRequestParams.get("name"));
                user.setAge(Integer.parseInt(allRequestParams.get("age")));
                user.setEmail(allRequestParams.get("email"));
                result = String.valueOf(user.updateById());
                break;
            default:
                List<User> list = user.selectAll();
                result = JSON.toJSONString(list);
        }
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " result:" + result;
    }

    @GetMapping("/set-traffic-attribute")
    public String set_traffic_attribute(@RequestParam Map<String, String> allRequestParams) {
        String slowRT = allRequestParams.get("rt");
        String slowRation = allRequestParams.get("slowRation");
        String exceptionRation = allRequestParams.get("exceptionRation");

        String responseMessage = "";

        try {
            Integer iSlowRT = Integer.parseInt(slowRT);
            responseMessage += "Adjust RT " + iSlowRT + "ms ";
            TrafficAttribute.getInstance().setRt(iSlowRT);
        } catch (NumberFormatException e) {
            ;
        }

        try {
            Integer iSlowRation = Integer.parseInt(slowRation);
            TrafficAttribute.getInstance().setSlowRation(iSlowRation);
            responseMessage += "Adjust slow ration to " + iSlowRation + "% ";
        } catch (NumberFormatException e) {
            ;
        }

        try {
            Integer iExceptionRation = Integer.parseInt(exceptionRation);
            TrafficAttribute.getInstance().setExceptionRation(iExceptionRation);
            responseMessage += "Adjust exception ration to " + iExceptionRation + "% ";
        } catch (NumberFormatException e) {
            ;
        }

        return responseMessage;
    }


    private void silentSleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

}
