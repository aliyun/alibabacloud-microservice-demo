package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.cartserviceapi.domain.CartItem;
import com.alibabacloud.hipstershop.checkoutserviceapi.domain.Order;
import com.alibabacloud.hipstershop.dao.CartDAO;
import com.alibabacloud.hipstershop.dao.OrderDAO;
import com.alibabacloud.hipstershop.dao.ProductDAO;
import com.alibabacloud.hipstershop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Random;

/**
 * @author wangtao 2019-08-12 15:41
 */
@Controller
public class AppController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private OrderDAO orderDAO;

    private Random random = new Random(System.currentTimeMillis());

    private String userID = "Test User";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productDAO.getProductList());
        return "index.html";
    }

    @GetMapping("/setExceptionByIp")
    public String setExceptionByIp(@RequestParam(name = "ip", required = false, defaultValue = "") String ip, Model model) {
        try {
            OutlierController.allNum++;
            productDAO.setExceptionByIp(ip);
        } catch (Exception e) {
            OutlierController.exceptionNum++;
            throw e;
        }
        model.addAttribute("products", productDAO.getProductList());
        return "index.html";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }

    @GetMapping("/setUser")
    public String user(@RequestParam(name = "userId", required = false) String userId, Model model) {
        userID = userId;
        return "index.html";
    }


    @GetMapping("/exception")
    public String exception() {

        throw new RuntimeException();
    }

    @GetMapping("/exception2")
    public String excpetion2() {

        int i = 20 / 0;
        return "hello";
    }

    @PostMapping("/checkout")
    public RedirectView checkout(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "street_address") String streetAddress,
                                 @RequestParam(name = "zip_code") String zipCode,
                                 @RequestParam(name = "city") String city,
                                 @RequestParam(name = "state") String state,
                                 @RequestParam(name = "credit_card_number") String creditCardNumber,
                                 @RequestParam(name = "credit_card_expiration_month") int creditCardExpirationMonth,
                                 @RequestParam(name = "credit_card_cvv") String creditCardCvv) {
        String orderId = orderDAO.checkout(email, streetAddress, zipCode, city, state, creditCardNumber,
                creditCardExpirationMonth, creditCardCvv, userID);
        return new RedirectView("/checkout/" + orderId);
    }

    @GetMapping("/checkout/{orderId}")
    public String checkout(@PathVariable(name = "orderId") String orderId, Model model) {
        Order order = orderDAO.getOrder(orderId, userID);
        model.addAttribute("order", order);
        return "checkout.html";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable(name = "id") String id, Model model) {
        Product p = productDAO.getProductById(id);
        model.addAttribute("product", p);
        return "product.html";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartItem> items = cartDAO.viewCart(userID);
        for (CartItem item : items) {
            Product p = productDAO.getProductById(item.getProductID());
            item.setProductName(p.getName() + item.getProductName());
            item.setPrice(p.getPrice());
            item.setProductPicture(p.getPicture());
        }
        model.addAttribute("items", items);
        return "cart.html";
    }

    @PostMapping("/cart")
    public RedirectView addToCart(@RequestParam(name = "product_id") String productID,
                                  @RequestParam(name = "quantity") int quantity) {
        cartDAO.addToCart(userID, productID, quantity);
        return new RedirectView("/cart");
    }

    @ModelAttribute("cartSize")
    public int getCartSize() {
        return cartDAO.viewCart(userID).size();
    }

}
