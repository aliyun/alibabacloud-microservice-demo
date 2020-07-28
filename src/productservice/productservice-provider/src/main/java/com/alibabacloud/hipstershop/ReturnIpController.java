package com.alibabacloud.hipstershop;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yizhan.xj
 */

@RestController
public class ReturnIpController {

    @ModelAttribute
    public void setVaryResponseHeader(HttpServletResponse response) {
        response.setHeader("APP_NAME", ProductServiceApplication.APP_NAME);
        response.setHeader("SERVICE_TAG", ProductServiceApplication.SERVICE_TAG);
        response.setHeader("SERVICE_IP", registration.getHost());
    }

    @Autowired
    private Registration registration;

    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getIp( @RequestParam("name")  String name, @RequestParam("age")  int age ) {
        return registration.getHost();
    }
}
