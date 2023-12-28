package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.api.IComponentAPI;
import com.alibaba.arms.mock.api.Invocation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author aliyun
 * @date 2021/06/10
 */
@Service
@Slf4j
public class ComponentService {

    /**
     * 当前服务名称
     */
    @Value("${service.name}")
    private String serviceName;

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
    private Map<String, Map<ApiType, APIWrapper>> serviceAddr = new HashMap<>();
    private Random random = new Random();
    @Autowired
    private List<IComponent> components;

    private Map<String, IComponent> componentMap = new HashMap<>();


    public IComponent getComponent(String componentName) {
        return componentMap.get(componentName.toLowerCase());
    }

    @PostConstruct
    public void init() {

        for (IComponent component : components) {
            componentMap.put(component.getComponentName().toLowerCase(), component);
        }

        List<String> services = Arrays.asList(this.services.split(","));
        List<String> serverUrls = Arrays.asList(this.serverUrls.split(","));
        for (int i = 0; i < services.size(); i++) {
            Map<ApiType, APIWrapper> apis = new HashMap<>();
            apis.put(ApiType.NORMAL, createBy(ApiType.NORMAL, serverUrls.get(i)));
            serviceAddr.put(services.get(i).toLowerCase(), apis);
        }
    }

    /**
     * @param apiType   正常调用api，超时时间为30分钟,慢调用api，超时时间为5s
     * @param serverUrl
     * @return
     */
    private APIWrapper createBy(ApiType apiType, String serverUrl) {

        int timeOut = 30;
        Map<ApiType, IComponentAPI> apis = new HashMap<>();
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

        return new APIWrapper(retrofit.create(IComponentAPI.class), serverUrl);
    }

    public String execute(List<Invocation> invocations, Integer level) {
        if (null == invocations) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Invocation invocation : invocations) {
            sb.append(route(invocation, level)).append("\n");
        }
        return sb.toString();
    }

    private String indent(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    String executeLocal(Invocation invocation, int level) {
        String indent = indent(level);
        //当前服务执行
        IComponent component = this.getComponent(invocation.getComponent());
        boolean hasChildren = (null == invocation.getChildren() || invocation.getChildren().isEmpty()) ? false : true;
        switch (invocation.getMethod()) {
            case "success":
                component.execute();
                break;
            default:
                component.execute();
                break;
        }
        return indent + invocation.getService() + ":" + invocation.getComponent() + "@" + invocation.getMethod();
    }

    String executeRemote(Invocation invocation, int level) {
        Map<ApiType, APIWrapper> apis = serviceAddr.get(invocation.getService().toLowerCase());
        APIWrapper api = apis.get(ApiType.NORMAL);
        String nextCall = invocation.getService().toLowerCase() + ":" + invocation.getComponent() + "@" + invocation.getMethod();
        Response<String> resp = null;
        try {
            if (null != invocation.getChildren() && !invocation.getChildren().isEmpty()) {
                resp = api.getApi().invokeChildren(invocation.getComponent().toLowerCase(), invocation.getMethod().toLowerCase(),
                        invocation.getChildren(), level).execute();
            } else {
                resp = api.getApi().invokeChildren(invocation.getComponent().toLowerCase(), invocation.getMethod().toLowerCase()).execute();
            }
        } catch (IOException e) {
            log.error("error", e);
            throw new RuntimeException(e.getMessage());
        }
        if (resp.isSuccessful()) {
            return nextCall + "\n" + resp.body();
        } else {
            try {
                String errMsg = resp.errorBody().string();
                log.error("execute remote failed msg: {}", errMsg);
                throw new RuntimeException(errMsg);
            } catch (IOException e) {
                log.error("error", e);
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    String route(Invocation invocation, int level) {
        if (invocation.getService().equalsIgnoreCase(serviceName)) {
            String sb = executeLocal(invocation, level);
            if (null != invocation.getChildren()) {
                for (Invocation each : invocation.getChildren()) {
                    sb = sb + "\n" + route(each, level + 1);
                }
            }
            return sb;
        } else {
            return executeRemote(invocation, level);
        }
    }

    /**
     * 随机挑选一个远端服务进行执行
     *
     * @param invocation
     * @param level
     * @return
     */
    public String randomRemoteCall(Invocation invocation, int level) {
        List<String> services = new ArrayList<>(serviceAddr.keySet());
        services.remove(this.serviceName.toLowerCase());
        String targetService = services.get(random.nextInt(services.size()));
        invocation.setService(targetService);
        return this.executeRemote(invocation, level);
    }

    enum ApiType {
        NORMAL,
        TIMEOUT;
    }

    @Data
    private static class APIWrapper {
        private final IComponentAPI api;
        private final String serverUrl;
    }
}
