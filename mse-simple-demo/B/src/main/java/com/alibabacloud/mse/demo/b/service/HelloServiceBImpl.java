package com.alibabacloud.mse.demo.b.service;

import com.alibabacloud.mse.demo.c.service.HelloServiceC;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;

<<<<<<< HEAD:mse-simple-demo/B/src/main/java/com/alibabacloud/mse/demo/service/HelloServiceBImpl.java
import static com.alibabacloud.mse.demo.BApplication.SERVICE_TAG;

@Service(version = "1.1.0")
=======
@Service(version = "1.2.0")
>>>>>>> master:mse-simple-demo/B/src/main/java/com/alibabacloud/mse/demo/b/service/HelloServiceBImpl.java
public class HelloServiceBImpl implements HelloServiceB {

    @Autowired
    InetUtils inetUtils;

<<<<<<< HEAD:mse-simple-demo/B/src/main/java/com/alibabacloud/mse/demo/service/HelloServiceBImpl.java
    @Reference(application = "${dubbo.application.id}", version = "1.1.0")
=======
    @Autowired
    String serviceTag;

    @Reference(application = "${dubbo.application.id}", version = "1.2.0")
>>>>>>> master:mse-simple-demo/B/src/main/java/com/alibabacloud/mse/demo/b/service/HelloServiceBImpl.java
    private HelloServiceC helloServiceC;

    @Override
    public String hello(String name) {
        return "B" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " +
                helloServiceC.hello(name);
    }

}
