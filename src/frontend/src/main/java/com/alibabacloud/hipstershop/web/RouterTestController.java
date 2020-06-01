package com.alibabacloud.hipstershop.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.alibabacloud.hipstershop.common.CommonUtil;
import com.alibabacloud.hipstershop.dao.CartDAO;
import com.alibabacloud.hipstershop.dao.ProductDAO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.alibabacloud.hipstershop.common.CommonUtil.*;

/**
 * @author yizhan.xj
 */

@RestController
@RequestMapping("/router")
public class RouterTestController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CartDAO cartDAO;

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/dubbo", method = RequestMethod.GET)
    public String dubbo(
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        return cartDAO.getRemoteIp(name, age);
    }

    @RequestMapping(value = "/springcloud", method = RequestMethod.GET)
    public String springcloud(
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        return productDAO.getRemoteIp(name, age);
    }

    @RequestMapping(value = "/update/dubbo")
    public String updateDubbo(
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age) {

        dubbo_age = age;
        dubbo_name = name;
        return "ok";
    }

    @RequestMapping(value = "/update/springcloud")
    public String updateSc(
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "age", required = false, defaultValue = "0") int age) {
        spring_cloud_age = age;
        spring_cloud_name = name;
        return "ok";
    }

    @RequestMapping(value = "/begin", method = RequestMethod.GET)
    public String begin() {

        if (ROUTER_BEGIN.get()) {
            return "router test already began";
        }

        INVOKER_ENABLE.set(true);

        EXECUTOR_SERVICE.submit((Runnable)() -> {
            while (INVOKER_ENABLE.get()) {
                try {
                    HttpUriRequest request = new HttpGet(
                        "http://127.0.0.1:" + port + "/router/dubbo?name=" + dubbo_name + "&age=" + dubbo_age);
                    CloseableHttpResponse response = HttpClients.createDefault().execute(request);
                    String result = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .readLine();
                    synchronized (DUBBO_LOCK) {
                        DUBBO_RESULT_QUEUE.add(result);
                    }
                } catch (Exception ignore) {}

            }
        });

        EXECUTOR_SERVICE.submit((Runnable)() -> {
            while (INVOKER_ENABLE.get()) {
                try {
                    HttpUriRequest request = new HttpGet(
                        "http://127.0.0.1:" + port + "/router/springcloud?name=" + dubbo_name + "&age=" + dubbo_age);
                    CloseableHttpResponse response = HttpClients.createDefault().execute(request);
                    String result = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .readLine();
                    synchronized (SPRING_CLOUD_LOCK) {
                        SPRING_CLOUD_RESULT_QUEUE.add(result);
                    }
                } catch (Exception ignore) {}

            }
        });

        ROUTER_BEGIN.set(true);
        return "begin now";

    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stop() {

        INVOKER_ENABLE.set(false);
        ROUTER_BEGIN.set(false);

        synchronized (DUBBO_LOCK) {
            DUBBO_RESULT_QUEUE.clear();
        }

        synchronized (SPRING_CLOUD_LOCK) {
            SPRING_CLOUD_RESULT_QUEUE.clear();
        }



        return "router test stop success";
    }

}
