package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.mock.api.Invocation;
import org.springframework.util.StringUtils;

/**
 * @author aliyun
 * @date 2021/10/29
 */
public interface ICase {

    public String getCaseName();

    public String run(Integer durationSeconds, int parallel);
    
    default String run(Integer durationSeconds, int parallel, Invocation invocation){ return "";};

    public String stop();

    public String injectError();

    public String stopInjectError();

    public String injectSlow(Double slowSeconds);

    public String stopInjectSlow();

    default String injectBig(Double size){
        return "";
    }

    default String stopInjectBig(){
        return "";
    }
}
