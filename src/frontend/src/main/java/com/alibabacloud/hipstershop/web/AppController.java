package com.alibabacloud.hipstershop.web;

import com.alibabacloud.hipstershop.dao.ProductDAO;
import com.alibabacloud.hipstershop.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangtao 2019-08-12 15:41
 */
@Controller
public class AppController {

    @Autowired
    private ProductDAO productDAO;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("cartSize", 1);
        model.addAttribute("products", productDAO.getProductList());
        return "index.html";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "home";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable(name="id") String id, Model model) {
        Product p = productDAO.getProductById(id);
        model.addAttribute("cartSize", 1);
        model.addAttribute("product", p);
        return "product.html";
    }

}
