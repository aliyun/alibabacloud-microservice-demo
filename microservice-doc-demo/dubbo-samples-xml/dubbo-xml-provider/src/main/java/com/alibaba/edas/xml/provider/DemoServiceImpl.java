package com.alibaba.edas.xml.provider;

import com.alibaba.edas.xml.DemoService;

import org.apache.dubbo.rpc.RpcContext;

public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

}
