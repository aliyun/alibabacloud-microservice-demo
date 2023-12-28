package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.mock.api.IComponentAPI;
import lombok.Data;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author aliyun
 * @date 2021/10/29
 */
@Service
public class ServiceRoute {

    /**
     * 所有服务名称
     */
    @Value("${service.names}")
    private String services;

    /**
     * 所有服务地址
     */
    @Value("${service.serverUrls}")
    private String serverUrls;

    private Map<String, IComponentAPI> serviceAddr = new HashMap<>();

    @Data
    private static class APIWrapper {
        private final IComponentAPI api;
        private final String serverUrl;
    }

    @PostConstruct
    public void init() {
        List<String> services = Arrays.asList(this.services.split(","));
        List<String> serverUrls = Arrays.asList(this.serverUrls.split(","));
        for (int i = 0; i < services.size(); i++) {
            IComponentAPI api = createBy(serverUrls.get(i));
            serviceAddr.put(services.get(i).toLowerCase(), api);
        }
    }

    public IComponentAPI getServiceAPI(String service) {
        return serviceAddr.get(service.toLowerCase());
    }


    private IComponentAPI createBy(String serverUrl) {

        int timeOut = 5;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IComponentAPI.class);
    }
}
