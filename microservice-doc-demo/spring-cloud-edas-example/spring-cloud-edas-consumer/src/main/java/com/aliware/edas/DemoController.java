package com.aliware.edas;

import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EchoService echoService;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Boolean ping() {
        try {
            String pong = echoService.echo("ping");

            System.out.println("Service returned: " + pong);
            return pong.contains("ping");
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    public String feign1(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = echoService.echo(str);

        long end = System.currentTimeMillis();
        return "" + start + " Consumer received." +
                "\t" + result +
                "\r\n" + end + " Consumer Return";
    }

    @RequestMapping(value = "/consumer/alive", method = RequestMethod.GET)
    public boolean alive() {
        return true;
    }

    @RequestMapping(value = "/consumer-echo/feign/{str}", method = RequestMethod.GET)
    public String feign2(@PathVariable String str) {
        return echoService.echo(str) + " By feign.";
    }
}
