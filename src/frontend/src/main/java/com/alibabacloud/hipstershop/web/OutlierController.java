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
    private int sleepTime = 100;

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
    public String begin(Model model) {
        begin.set(true);

        REFRESH_EXECUTOR.submit((Runnable)() -> {
            while (begin.get()) {
                try {
                    allNum++;
                    Thread.sleep(sleepTime);

                    cartDAO.setExceptionByIp(exceptionIp);
                } catch (Exception exception) {
                    exceptionNum++;
                }
            }
        });

        show(model);
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/stop", method = RequestMethod.GET)
    public String stop(Model model) {
        begin.set(false);
        show(model);
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/time", method = RequestMethod.GET)
    public String time(Model model, int time) {
        sleepTime = time;
        show(model);
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/setException", method = RequestMethod.GET)
    public String setException(Model model, String ip) {
        exceptionIp = ip;
        show(model);
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/result", method = RequestMethod.GET)
    public String outlierResult(Model model) {
        show(model);
        return "outlier.html";
    }

    @RequestMapping(value = "/outlier/clear", method = RequestMethod.GET)
    public String clear(Model model) {
        allNum = 0;
        exceptionNum = 0;
        show(model);
        return "outlier.html";
    }

    private void show(Model model) {
        model.addAttribute("all_num", allNum);
        model.addAttribute("exception_ip", exceptionIp);
        model.addAttribute("exception_num", exceptionNum);
        model.addAttribute("sleep_time", sleepTime);
    }
}
