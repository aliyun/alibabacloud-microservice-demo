package com.alibabacloud.mse.demo.a.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class MyHelloJob extends JavaProcessor {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    String serviceTag;

    @Autowired
    InetUtils inetUtils;

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        log.info("MyHelloJob process start:{}", JSON.toJSONString(context));
        String result = restTemplate.getForObject("http://sc-B/b", String.class);

        String res = "A[tag=" + serviceTag + "][" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + result;

        log.info("MyHelloJob process res:{}", res);
        return new ProcessResult(true);
    }
}
