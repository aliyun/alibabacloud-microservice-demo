package com.alibabacloud.mse.demo.a;

import org.springframework.stereotype.Component;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import com.alibabacloud.mse.demo.a.AController;

@Component
public class SchedulerXJob extends JavaProcessor {
    @Autowired
    private AController aController;

    @Override
    public ProcessResult process(JobContext context) throws Exception {
        System.out.println(context);

        String result = aController.a();
        System.out.println(result);

        return new ProcessResult(true);
    }
}