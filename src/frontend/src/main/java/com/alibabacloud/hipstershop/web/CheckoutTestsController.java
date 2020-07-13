package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/9
 */
@RestController
public class CheckoutTestsController {

    @Resource
    private OrderDAO orderDAO;

    private String userID = "Test User";

    @PostMapping("/checkout_test")
    public String checkout(@RequestParam(name="email") String email,
                                 @RequestParam(name="street_address") String streetAddress,
                                 @RequestParam(name="zip_code") String zipCode,
                                 @RequestParam(name="city") String city,
                                 @RequestParam(name="state") String state,
                                 @RequestParam(name="credit_card_number") String creditCardNumber,
                                 @RequestParam(name="credit_card_expiration_month") int creditCardExpirationMonth,
                                 @RequestParam(name="credit_card_cvv") String creditCardCvv) {
        String orderId = orderDAO.checkout(email, streetAddress, zipCode, city, state, creditCardNumber,
                creditCardExpirationMonth, creditCardCvv, userID);
        return orderId;
    }
}
