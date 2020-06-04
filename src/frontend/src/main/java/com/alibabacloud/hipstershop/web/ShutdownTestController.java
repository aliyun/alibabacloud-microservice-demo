package com.alibabacloud.hipstershop.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import static com.alibabacloud.hipstershop.common.CommonUtil.ROUTER_BEGIN;
import static com.alibabacloud.hipstershop.web.RouterTestController.startInvoke;
import static com.alibabacloud.hipstershop.web.RouterTestController.stopInvoker;

/**
 * @author yizhan.xj
 */

@RestController
@RequestMapping("/shutdown")
public class ShutdownTestController {

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/begin", method = RequestMethod.GET)
    public RedirectView begin(RedirectAttributes redirectAttributes) {

        if (ROUTER_BEGIN.get()) {
            redirectAttributes.addFlashAttribute("message", "已开启");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return new RedirectView("/shutdown/result");
        }

        startInvoke(port);
        redirectAttributes.addFlashAttribute("message", "已开启");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return new RedirectView("/shutdown/result");

    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public RedirectView stop(RedirectAttributes redirectAttributes) {

        stopInvoker();

        redirectAttributes.addFlashAttribute("message2", "已停止");
        redirectAttributes.addFlashAttribute("alertClass2", "alert-success");
        return new RedirectView("/shutdown/result");
    }

}
