package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.mock.api.IComponentAPI;
import com.alibaba.arms.mock.api.dto.BigKeyTroubleRequest;
import com.alibaba.arms.mock.api.dto.ErrorTroubleRequest;
import com.alibaba.arms.mock.api.dto.SlowTroubleRequest;
import com.alibaba.arms.mock.api.dto.StupidTroubleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;

import java.io.IOException;

/**
 * @author aliyun
 * @date 2021/10/29
 */
@Slf4j
public abstract class BaseTestCase implements ICase {

    @Autowired
    protected CaseRunner caseRunner;
    @Autowired
    protected ServiceRoute serviceRoute;

    @Override
    public String stop() {
        return caseRunner.stop(this.getCaseName());
    }


    protected String injectError(String service, String componentName) {
        log.info("inject error service={},comp={}", service, componentName);
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        try {
            ErrorTroubleRequest errorTroubleRequest = new ErrorTroubleRequest(componentName);
            Response<String> resp = componentAPI.injectError(errorTroubleRequest).execute();
            if (resp.isSuccessful()) {
                return "inject success";
            } else {
                log.error("inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
            }
        } catch (IOException e) {
            log.error("inject error", e);
            return "inject failed";
        }
        return "inject failed";
    }

    protected String stopInjectError(String service, String componentName) {
        log.info("stop inject error service={},comp={}", service, componentName);
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        try {
            Response<String> resp = componentAPI.cancelInject("error", componentName).execute();
            if (resp.isSuccessful()) {
                return "stop inject success";
            } else {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
            }
        } catch (IOException e) {
            log.error("stop inject error", e);
            return "stop inject failed";
        }
        return "stop inject failed";
    }


    protected String injectSlow(String service, String componentName, String slowSeconds) {
        log.info("inject slow service={},comp={}, slowSeconds={}", service, componentName, slowSeconds);
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        try {
            SlowTroubleRequest slowTroubleRequest = new SlowTroubleRequest(componentName);
            slowTroubleRequest.setSlowInSeconds(slowSeconds);
            Response<String> resp = componentAPI.injectSlow(slowTroubleRequest).execute();
            if (resp.isSuccessful()) {
                return "inject success";
            } else {
                log.error("inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
            }
        } catch (IOException e) {
            log.error("inject error", e);
            return "inject failed";
        }
        return "inject failed";
    }


    protected String stopInjectSlow(String service, String componentName) {
        log.info("stop inject slow service={},comp={}", service, componentName);
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        try {
            Response<String> resp = componentAPI.cancelInject("slow", componentName).execute();
            if (resp.isSuccessful()) {
                return "stop inject success";
            } else {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
            }
        } catch (IOException e) {
            log.error("stop inject error", e);
            return "stop inject failed";
        }
        return "stop inject failed";
    }

    public String injectBig(String service, String componentName, String size) {
        log.info("inject big service={},comp={}, size={}", service, componentName, size);
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        try {
            BigKeyTroubleRequest bigKeyTroubleRequest = new BigKeyTroubleRequest(componentName);
            bigKeyTroubleRequest.setSize(size);
            Response<String> resp = componentAPI.injectBig(bigKeyTroubleRequest).execute();
            if (resp.isSuccessful()) {
                return "inject success";
            } else {
                log.error("inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
            }
        } catch (IOException e) {
            log.error("inject error", e);
            return "inject failed";
        }
        return "inject failed";
    }

    public String stopInjectBig(String service, String componentName) {
        log.info("stop inject big service={},comp={}", service, componentName);
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        try {
            Response<String> resp = componentAPI.cancelInject("big", componentName).execute();
            if (resp.isSuccessful()) {
                return "stop inject success";
            } else {
                log.error("stop inject failed, status={},msg={}", resp.code(), resp.errorBody().string());
            }
        } catch (IOException e) {
            log.error("stop inject error", e);
            return "stop inject failed";
        }
        return "stop inject failed";
    }
}
