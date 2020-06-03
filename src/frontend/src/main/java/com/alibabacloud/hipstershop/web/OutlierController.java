package com.alibabacloud.hipstershop.web;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibabacloud.hipstershop.dao.CartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OutlierController {

    private AtomicBoolean begin = new AtomicBoolean(false);
    public static long exceptionNum = 0;
    public static long allNum = 0;
    private static String exceptionIp = "";

    @Autowired
    private CartDAO cartDAO;

    @Value("${server.port}")
    private String port;

    private static final ScheduledExecutorService REFRESH_EXECUTOR = Executors.newScheduledThreadPool(1,
        new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("dubbo.outlier.refresh-" + thread.getId());
                return thread;
            }
        });


    @RequestMapping(value = "/outlier/begin", method = RequestMethod.GET)
    public String begin() {
        begin.set(true);
        REFRESH_EXECUTOR.submit((Runnable)() -> {
            while (begin.get()) {
                try {
                    allNum++;
                    Thread.sleep(100);

                    cartDAO.setExceptionByIp(exceptionIp);
                } catch (Exception exception) {
                    exceptionNum++;
                }
            }
        });
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/stop", method = RequestMethod.GET)
    public String stop(Model model) {
        begin.set(false);
        model.addAttribute("all_num",allNum);
        model.addAttribute("exception_num",exceptionNum);
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/setException", method = RequestMethod.GET)
    public String setException(Model model, String ip) {
        exceptionIp = ip;
        model.addAttribute("exception_ip",exceptionIp);
        return "outlier.html";
    }


    @RequestMapping(value = "/outlier/result", method = RequestMethod.GET)
    public String outlierResult(Model model) {
        begin.set(false);
        model.addAttribute("all_num",allNum);
        model.addAttribute("exception_num",exceptionNum);
        return "outlier.html";
    }
}
