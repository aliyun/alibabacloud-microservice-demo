package com.alibaba.edas;

import com.alibaba.boot.hsf.annotation.HSFProvider;


@HSFProvider(serviceInterface = HelloService.class, serviceVersion = "1.0.0")
public class HellpServiceImpl implements HelloService {
    @Override
    public String echo(String string) {
        return string;
    }
}
