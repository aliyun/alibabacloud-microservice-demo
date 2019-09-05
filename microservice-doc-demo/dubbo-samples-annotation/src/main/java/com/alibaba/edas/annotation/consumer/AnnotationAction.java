package com.alibaba.edas.annotation.consumer;

import com.alibaba.edas.annotation.GreetingService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component("annotationAction")
public class AnnotationAction {

    @Reference
    private GreetingService greetingService;

    public String doGreeting(String name) {
        return greetingService.greeting(name);
    }

}
