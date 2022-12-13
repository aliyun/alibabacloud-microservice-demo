package com.karl.pre.controller;

//import com.karl.pre.config.CommonTypeSwitch;

//import com.aliyun.ahas20180901.Client;
//import com.aliyun.ahas20180901.models.CreateLicenseKeyRequest;
//import com.aliyun.ahas20180901.models.CreateLicenseKeyResponse;
//import com.aliyun.teaopenapi.models.Config;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.ahas_openapi.model.v20190901.ListFlowRulesOfAppRequest;
//import com.aliyuncs.ahas_openapi.model.v20190901.ListFlowRulesOfAppResponse;
//import com.aliyuncs.profile.DefaultProfile;

import com.karl.pre.utils.HttpClientUtils;
import com.karl.pre.utils.HttpHelper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class UserController {
    private static final Random RANDOM = new Random();
    private static final Logger logger = LoggerFactory.getLogger("commercialize");

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.provider.addr}")
    public String providerAddr;

    private String curIp;


    @Value("${request.frequency}")
    int frequency;

    private static final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));

    @PostConstruct
    private void init(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                curIp = getIp();

                silentSleep(3000);

                boolean openRestTemplate = Boolean.parseBoolean(System.getProperty("open.rest.template","true"));
                boolean openHttpClient = Boolean.parseBoolean(System.getProperty("open.http.client","false"));
                boolean openOkHttp = Boolean.parseBoolean(System.getProperty("open.ok.http","false"));

                HttpClient client = null;
                HttpUriRequest hello = null;
                if(openHttpClient) {
                    client = HttpClientUtils.getConnection();
                    hello = HttpClientUtils.getRequestMethod(null, providerAddr, "get");
                }

                while (true) {

                    if(openRestTemplate) {
                        try {
                            HttpHeaders headers = new HttpHeaders();
                            HttpEntity<String> entity = new HttpEntity<String>(headers);
                            String res = restTemplate.exchange(providerAddr, HttpMethod.GET, entity, String.class).getBody();

                            System.out.println("[REST_TEMPLATE] ------------>>>>>>>>>>>>>>>>>> time: " + dateFormatThreadLocal.get().format(System.currentTimeMillis()) + " ,with res" + res);
                        }catch (Throwable e){
                            System.out.println("[REST_TEMPLATE] ------------>>>>>>>>>>>>>>>>>> time: " + dateFormatThreadLocal.get().format(System.currentTimeMillis()) + " ,failed: " + e);
                            throw new RuntimeException();
                        }
                    }

                    if(openHttpClient){
                        try {
                            HttpResponse response = client.execute(hello);
                            org.apache.http.HttpEntity entity = response.getEntity();
                            String message = EntityUtils.toString(entity, "utf-8");
                            System.out.println("[C_HTTP_CLIENT] ------------>>>>>>>>>>>>>>>>>> time: "  + dateFormatThreadLocal.get().format(System.currentTimeMillis()) + " ,with res" + message);

                        }catch (Throwable e){
                            System.out.println("[C_HTTP_CLIENT] ------------>>>>>>>>>>>>>>>>>> time: " + dateFormatThreadLocal.get().format(System.currentTimeMillis()) + " ,failed: " + e);
                            throw new RuntimeException();
                        }
                    }

                    if(openOkHttp){
                        try {
                            String response = HttpHelper.get(providerAddr);
                            System.out.println("[C_OK_HTTP] ------------>>>>>>>>>>>>>>>>>> time: "  + dateFormatThreadLocal.get().format(System.currentTimeMillis()) + " ,with res" + response);

                        }catch (Throwable e){
                            System.out.println("[C_OK_HTTP] ------------>>>>>>>>>>>>>>>>>> time: " + dateFormatThreadLocal.get().format(System.currentTimeMillis()) + " ,failed: " + e);
                            throw new RuntimeException();
                        }
                    }

                    try {
                        silentSleep(1000 / frequency);
                    } catch (Throwable ignored) {
                    }
                }
            }
        }).start();

    }


    private String getIp(){
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    private void silentSleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }


    @GetMapping("/hello")
    public String hello(){
        int random = RANDOM.nextInt(100);
        String oriRes;
        if(random < 30) {
            oriRes= "BAD";
        } else if(random > 50) {
            oriRes= "PRE";
        } else {
            oriRes= "OK";
        }

        return "From ip:[" + curIp +"] with res : " + oriRes;
    }

    @GetMapping("/world")
    public ResponseEntity<String> world(){
        if(RANDOM.nextInt(100) < 30) {
            return new ResponseEntity<String>("W_BAD", HttpStatus.BAD_REQUEST);
        } else if(RANDOM.nextInt(100) > 50) {
            return new ResponseEntity<String>("W_PRE", HttpStatus.SERVICE_UNAVAILABLE);
        } else if(RANDOM.nextInt(100) > 70) {
            return new ResponseEntity<String>("W_SUP", HttpStatus.TEMPORARY_REDIRECT);
        }else {
            return new ResponseEntity<String>("W_OK", HttpStatus.OK);
        }
    }

}
