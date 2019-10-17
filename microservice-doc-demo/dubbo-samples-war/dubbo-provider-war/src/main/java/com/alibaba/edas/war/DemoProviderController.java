package com.alibaba.edas.war;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoProviderController {

    @RequestMapping(value = "/", produces = "text/html; charset=utf-8")
    public ModelAndView indexPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");

        return mv;
    }

}
