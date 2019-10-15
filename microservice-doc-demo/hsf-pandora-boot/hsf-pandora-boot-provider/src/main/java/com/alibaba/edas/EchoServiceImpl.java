package com.alibaba.edas;

import com.alibaba.boot.hsf.annotation.HSFProvider;


@HSFProvider(serviceInterface = EchoService.class, serviceVersion = "1.0.0")
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String string) {
        return string;
    }
}
