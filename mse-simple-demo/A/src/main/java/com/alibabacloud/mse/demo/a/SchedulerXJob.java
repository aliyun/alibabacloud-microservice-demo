package com.alibabacloud.mse.demo.a;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

@Component
public class SchedulerXJob extends JavaProcessor {
    @Override
    public ProcessResult process(JobContext context) throws Exception {
        System.out.println("hello schedulerx2.0");
        return new ProcessResult(true);
    }
}