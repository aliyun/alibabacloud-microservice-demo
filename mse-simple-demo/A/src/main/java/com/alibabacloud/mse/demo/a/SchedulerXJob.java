package com.alibabacloud.mse.demo.a;

import org.springframework.stereotype.Component;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.alibabacloud.mse.demo.a.AController;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SchedulerXJob extends JavaProcessor {
    private static final Logger log = LoggerFactory.getLogger(SchedulerXJob.class);

    @Autowired
    private AController aController;

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        String result = aController.a();
        System.out.println("[SchedulerXJob.process] scheduler x job process result is: " + result);
        return new ProcessResult(true);
    }
}