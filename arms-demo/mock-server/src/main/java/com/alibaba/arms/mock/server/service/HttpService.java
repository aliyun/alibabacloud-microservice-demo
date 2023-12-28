package com.alibaba.arms.mock.server.service;

import com.alibaba.arms.mock.api.IComponentAPI;
import com.alibaba.arms.mock.api.dto.ErrorTroubleRequest;
import com.alibaba.arms.mock.api.dto.SlowTroubleRequest;
import com.alibaba.arms.mock.server.domain.AutoResetObjectWrapper;
import com.alibaba.arms.mock.server.service.trouble.IServiceTroubleInjector;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author aliyun
 * @date 2020/06/17
 */
@Service
@Slf4j
public class HttpService extends AbstractComponent implements IServiceTroubleInjector {

    public AutoResetObjectWrapper<Boolean> slowFlag = new AutoResetObjectWrapper<>(false);
    
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    @Value("${external.service.addr}")
    private String externalServiceAddr;
    private IComponentAPI externalServiceAPI;

    @Value("${external.service.connection.addr}")
    private String externalServiceAddrForConnection;
    private IComponentAPI externalServiceAPIForConnection;

    @PostConstruct
    public void init() {
        if (!externalServiceAddr.endsWith("/")) {
            externalServiceAddr = externalServiceAddr + "/";
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(externalServiceAddr)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        externalServiceAPI = retrofit.create(IComponentAPI.class);
        if (!externalServiceAddrForConnection.endsWith("/")) {
            externalServiceAddrForConnection = externalServiceAddrForConnection + "/";
        }
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(externalServiceAddrForConnection)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        externalServiceAPIForConnection = retrofit2.create(IComponentAPI.class);
    }

    @Override
    public void injectError(Map<String, String> params) {
        ErrorTroubleRequest errorTroubleRequest = new ErrorTroubleRequest("local");
        try {
            Response<String> resp = externalServiceAPI.injectError(errorTroubleRequest).execute();
            if (!resp.isSuccessful()) {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
                throw new RuntimeException("inject failed");
            }
        } catch (Exception e) {
            log.error("stop inject error", e);
            throw new RuntimeException("inject failed");
        }
    }

    @Override
    public void cancelInjectError() {
        try {
            Response<String> resp = externalServiceAPI.cancelInject("error", "local").execute();
            if (!resp.isSuccessful()) {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
                throw new RuntimeException("inject failed");
            }
        } catch (Exception e) {
            log.error("stop inject error", e);
            throw new RuntimeException("inject failed");
        }
    }

    @Override
    public void injectSlow(Map<String, String> params, String seconds) {
        this.slowFlag.update(true);
        // 注入慢在local
        SlowTroubleRequest slowTroubleRequest = new SlowTroubleRequest("local");
        slowTroubleRequest.setSlowInSeconds(seconds);
        try {
            Response<String> resp = externalServiceAPI.injectSlow(slowTroubleRequest).execute();
            if (!resp.isSuccessful()) {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
                throw new RuntimeException("inject failed");
            }
        } catch (Exception e) {
            log.error("stop inject error", e);
            throw new RuntimeException("inject failed");
        }
    }
    public void injectSlowForMysqlConnect(Map<String, String> params, String seconds) {
        this.slowFlag.update(true);
        SlowTroubleRequest slowTroubleRequest = new SlowTroubleRequest("local");
        slowTroubleRequest.setSlowInSeconds(seconds);
        try {
            Response<String> resp = externalServiceAPIForConnection.injectSlow(slowTroubleRequest).execute();
            if (!resp.isSuccessful()) {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
                throw new RuntimeException("inject failed");
            }
        } catch (Exception e) {
            log.error("stop inject error", e);
            throw new RuntimeException("inject failed");
        }
    }

    @Override
    public void cancelInjectSlow() {
        try {
            this.slowFlag.update(false);
            Response<String> resp = externalServiceAPI.cancelInject("slow", "local").execute();
            if (!resp.isSuccessful()) {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
                throw new RuntimeException("inject failed");
            }
        } catch (Exception e) {
            log.error("stop inject error", e);
            throw new RuntimeException("inject failed");
        }
    }

    @Override
    public String getComponentName() {
        return "http";
    }

    @Override
    public void execute() {
        try {
            Response<String> response = externalServiceAPI.invokeChildren("local", "success").execute();
            if (!response.isSuccessful()) {
                log.error("invoke external failed", response.errorBody());
                throw new RuntimeException("invoke external failed");
            } else {
                log.info("invoke external success", response.body());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeForConnect() {
        try {
            Response<String> response = externalServiceAPIForConnection.invokeChildren("local", "success").execute();
            if (!response.isSuccessful()) {
                log.error("invoke external failed", response.errorBody());
                throw new RuntimeException("invoke external failed");
            } else {
                log.info("invoke external success", response.body());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Class getImplClass() {
        return HttpService.class;
    }
}
