package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.common.AccessCountUtil;
import com.alibabacloud.hipstershop.dao.CartDAO;
import com.alibabacloud.hipstershop.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import static com.alibabacloud.hipstershop.common.CommonUtil.*;


/**
 * @author meisheng.lym
 */

@Controller
@RequestMapping("/auth")
public class AuthTestController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CartDAO cartDAO;

    @Value("8080")
    private String port;

    @RequestMapping(value = "/begin", method = RequestMethod.GET)
    public RedirectView begin(RedirectAttributes redirectAttributes) {
        if (AUTH_BEGIN.get()) {
            redirectAttributes.addFlashAttribute("message", "已开启");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            return new RedirectView("/auth/result");
        }

        AUTH_ENABLE.set(true);

        // 模拟 viewCart() 接口调用
        String uri = "http://127.0.0.1:" + port + "/cart";
        EXECUTOR_SERVICE.submit((Runnable)() -> {
            AccessCountUtil.uriAccess(uri, DUBBO_AUTH_LOCK, DUBBO_AUTH_RESULT_QUEUE);
        });

        // 模拟各个product的服务调用
        for (int i = 0; i < PRODUCT_NUM; i++) {
            String uri2 = "http://127.0.0.1:" + port + "/product/" + products[i];
            int finalI = i;
            EXECUTOR_SERVICE.submit((Runnable)() -> {
                AccessCountUtil.uriAccess(uri2, PRODUCT_LOCK[finalI], SPRING_CLOUD_AUTH_RESULT_QUEUE[finalI]);
            });
        }

        AUTH_BEGIN.set(true);

        redirectAttributes.addFlashAttribute("message", "开启中，请再次点击按钮查看结果");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return new RedirectView("/auth/result");
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public RedirectView stop(RedirectAttributes redirectAttributes) {
        if (!AUTH_BEGIN.get()) {
            redirectAttributes.addFlashAttribute("message2", "已停止");
            redirectAttributes.addFlashAttribute("alertClass2", "alert-success");
            return new RedirectView("/auth/result");
        }

        AUTH_ENABLE.set(false);
        AUTH_BEGIN.set(false);

        synchronized (DUBBO_AUTH_LOCK) {
            DUBBO_AUTH_RESULT_QUEUE.clear();
        }

        for (int i = 0; i < PRODUCT_NUM; i++) {
            synchronized (PRODUCT_LOCK[i]) {
                SPRING_CLOUD_AUTH_RESULT_QUEUE[i].clear();
            }
        }

        redirectAttributes.addFlashAttribute("message2", "停止成功");
        redirectAttributes.addFlashAttribute("alertClass2", "alert-success");
        return new RedirectView("/auth/result");
    }
}
