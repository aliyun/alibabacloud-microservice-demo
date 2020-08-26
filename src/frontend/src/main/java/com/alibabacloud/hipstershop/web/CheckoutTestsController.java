package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.common.CommonUtil;
import com.alibabacloud.hipstershop.dao.OrderDAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/9
 */
@RestController
public class CheckoutTestsController {

    @Resource
    private OrderDAO orderDAO;

    private static boolean exception = false;
    private static Set<String> exceptionIp = new HashSet<>();

    private String userID = "Test User";

    @PostMapping("/checkout_test")
    public String checkout(@RequestParam(name = "email") String email,
                           @RequestParam(name = "street_address") String streetAddress,
                           @RequestParam(name = "zip_code") String zipCode,
                           @RequestParam(name = "city") String city,
                           @RequestParam(name = "state") String state,
                           @RequestParam(name = "country") String country,
                           @RequestParam(name = "credit_card_number") String creditCardNumber,
                           @RequestParam(name = "credit_card_expiration_month") int creditCardExpirationMonth,
                           @RequestParam(name = "credit_card_cvv") String creditCardCvv) throws UnknownHostException {
        if(exception){
            String ip = CommonUtil.getLocalIp();
            if(exceptionIp.contains(ip)) {
                throw new RuntimeException();
            }
        }
        return orderDAO.checkout(email, streetAddress, zipCode, city, state, country, creditCardNumber,
                creditCardExpirationMonth, creditCardCvv, userID);
    }

    @PostMapping("/exception_checkout")
    public String checkoutException(@RequestParam("ip")String ip, @RequestParam("type")Integer type) {
        if(type == 1){
            exception = true;
            exceptionIp.add(ip);
        }
        else{
            exception = false;
            if(!"".equals(ip)){
                exceptionIp.remove(ip);
            }
        }
        return "success";
    }
}
