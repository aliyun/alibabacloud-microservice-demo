package com.aliware.edas;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @Autowired
    private EchoService echoService;

    @RequestMapping("/ping")
    public boolean ping() {
        try {
            return echoService.alive();

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return false;
    }

    @RequestMapping(value = "/provider-echo/{str}")
    public String pingPongPing(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = echoService.echo(str);

        String[] arrays = result.split("\r\n");

        StringBuffer sb = new StringBuffer(start + " Provider echo method received\r\n");
        for (String val : arrays) {
            sb.append("\t" + val).append("\r\n");
        }

        long end = System.currentTimeMillis();

        sb.append(end + " Provider echo method return.");

        return sb.toString();
    }

    @RequestMapping(value = "/echo/{str}")
    public String echo(@PathVariable String str) {
        long start = System.currentTimeMillis();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();
        return "\r\n\t" + start + " Provider received." +
                "\r\n\t\tProvider processed after sleep 1 second! Echo String: \"" + str + "\"" +
                "\r\n\t" + end + " Provider Return";
    }
}
