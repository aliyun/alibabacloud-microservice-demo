package com.alibabacloud.hipstershop.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.alibabacloud.hipstershop.common.CommonUtil.*;

/**
 * @author yizhan.xj
 */
@Controller
public class RouteDemoController {

    private static final Map<String, Integer> SPRING_CLOUD_RESULT_MAP = new ConcurrentHashMap<>();

    private static final List<ResultNode> SPRING_CLOUD_RESULT_LIST = new LinkedList<>();


    private static final Map<String, Integer> DUBBO_RESULT_MAP = new ConcurrentHashMap<>();

    private static final List<ResultNode> DUBBO_RESULT_LIST = new LinkedList<>();

    @GetMapping("/router/result")
    public String routerResult(Model model) {


        synchronized (SPRING_CLOUD_LOCK) {

            SPRING_CLOUD_RESULT_LIST.clear();
            SPRING_CLOUD_RESULT_MAP.clear();

            for (String str : SPRING_CLOUD_RESULT_QUEUE) {
                if (null == SPRING_CLOUD_RESULT_MAP.get(str)) {
                    SPRING_CLOUD_RESULT_MAP.put(str, 1);
                } else {
                    int count = SPRING_CLOUD_RESULT_MAP.get(str);
                    count++;
                    SPRING_CLOUD_RESULT_MAP.put(str, count);
                }
            }

            for (Map.Entry<String, Integer> entry : SPRING_CLOUD_RESULT_MAP.entrySet()) {
                SPRING_CLOUD_RESULT_LIST.add(new ResultNode(entry.getKey(), entry.getValue()));
            }


            model.addAttribute("spring_cloud_router_result", SPRING_CLOUD_RESULT_LIST);
        }
        synchronized (DUBBO_LOCK) {

            DUBBO_RESULT_LIST.clear();
            DUBBO_RESULT_MAP.clear();

            for (String str : DUBBO_RESULT_QUEUE) {
                if (null == DUBBO_RESULT_MAP.get(str)) {
                    DUBBO_RESULT_MAP.put(str, 1);
                } else {
                    int count = DUBBO_RESULT_MAP.get(str);
                    count++;
                    DUBBO_RESULT_MAP.put(str, count);
                }
            }

            for (Map.Entry<String, Integer> entry : DUBBO_RESULT_MAP.entrySet()) {
                DUBBO_RESULT_LIST.add(new ResultNode(entry.getKey(), entry.getValue()));
            }


            model.addAttribute("dubbo_router_result", DUBBO_RESULT_LIST);
        }

        model.addAttribute("spring_cloud_name", spring_cloud_name);
        model.addAttribute("spring_cloud_age", spring_cloud_age);
        model.addAttribute("dubbo_name", dubbo_name);
        model.addAttribute("dubbo_age", dubbo_age);

        return "router.html";
    }


    static class ResultNode {

        private String ip;
        private int times;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public ResultNode(String ip, int times) {
            this.ip = ip;
            this.times = times;
        }
    }

}
