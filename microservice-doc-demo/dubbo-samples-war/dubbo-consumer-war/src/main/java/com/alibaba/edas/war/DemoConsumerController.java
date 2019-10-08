package com.alibaba.edas.war;


import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DemoConsumerController {

    @Reference(check = false)
    private EchoService demoService;

    @RequestMapping(value = "/", produces = "text/html; charset=utf-8")
    public ModelAndView indexPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");

        return mv;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Boolean ping() {
        try {
            String pong = demoService.echo("ping");

            System.out.println("Service returned: " + pong);
            return pong.contains("ping");
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = demoService.echo(str);

        long end = System.currentTimeMillis();
        return "" + start + " Consumer received." +
            "\t" + result +
            "\r\n" + end + " Consumer Return";
    }
}
