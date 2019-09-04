package com.alibaba.edas.annotation.provider;

import com.alibaba.edas.annotation.GreetingService;

import org.apache.dubbo.config.annotation.Service;

@Service
public class AnnotationGreetingServiceImpl implements GreetingService {

    @Override
    public String greeting(String name) {
        return "Annotation, greeting " + name;
    }

    public String replyGreeting(String name) {
        return "Annotation, fine " + name;
    }

}
