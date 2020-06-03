package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.common.AccessCountUtil;
import com.alibabacloud.hipstershop.dao.CartDAO;
import com.alibabacloud.hipstershop.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.alibabacloud.hipstershop.common.CommonUtil.*;


/**
 * @author meisheng.lym
 */

@RestController
@RequestMapping("/auth")
public class AuthTestController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CartDAO cartDAO;

    @Value("8080")
    private String port;

    @RequestMapping(value = "/begin", method = RequestMethod.GET)
    public String begin() {
        if (AUTH_BEGIN.get()) {
            return "auth test already began";
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

        return "begin now";
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public String stop() {
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

        return "auth test stop success";
    }
}
