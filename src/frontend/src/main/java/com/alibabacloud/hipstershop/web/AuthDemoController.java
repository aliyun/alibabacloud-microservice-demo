package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.common.AccessCountUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.alibabacloud.hipstershop.common.CommonUtil.*;

/**
 * @author meisheng.lym
 */

@Controller
public class AuthDemoController {

    private static final Map<String, Integer> DUBBO_RESULT_MAP = new ConcurrentHashMap<>();

    private static final List<AccessCountUtil.ResultNode> DUBBO_RESULT_LIST = new LinkedList<>();


    private static final Map<String, Integer>[] SPRING_CLOUD_RESULT_MAP = new ConcurrentHashMap[6];

    private static final List<AccessCountUtil.ResultNode>[] SPRING_CLOUD_RESULT_LIST = new LinkedList[6];

    @GetMapping("/auth/result")
    public String authResult(Model model) {

        // 设置当queue中的访问都是同一个时，不显示次数
        boolean display = false;

        AccessCountUtil.putResult(DUBBO_AUTH_LOCK, DUBBO_RESULT_LIST, DUBBO_RESULT_MAP, DUBBO_AUTH_RESULT_QUEUE, display);

        model.addAttribute("dubbo_auth_result", DUBBO_RESULT_LIST);

        AccessCountUtil.initial();

        for (int i = 0; i < PRODUCT_NUM; i++) {

            if (SPRING_CLOUD_RESULT_LIST[i] == null) {
                SPRING_CLOUD_RESULT_LIST[i] = new LinkedList<>();
            }
            if (SPRING_CLOUD_RESULT_MAP[i] == null) {
                SPRING_CLOUD_RESULT_MAP[i] = new ConcurrentHashMap<>();
            }

            AccessCountUtil.putResult(PRODUCT_LOCK[i], SPRING_CLOUD_RESULT_LIST[i], SPRING_CLOUD_RESULT_MAP[i], SPRING_CLOUD_AUTH_RESULT_QUEUE[i], display);

            model.addAttribute("product"+i, SPRING_CLOUD_RESULT_LIST[i]);
        }
        return "auth.html";
    }

}
