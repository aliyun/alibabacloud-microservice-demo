package com.alibaba.edas.war;

import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;

@Service(timeout = 5000)
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String name) {
        long start = System.currentTimeMillis();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();
        return "\r\n\t" + start + " Provider received." +
                "\r\n\t\tProvider processed after sleep 1 second! Echo String: \"" + name + "\"" +
                "\r\n\t" + end + " Provider Return";

    }
}
