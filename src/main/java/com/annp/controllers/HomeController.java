/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Cart;
import com.annp.pojo.Category;
import com.annp.pojo.CategorySub;
import com.annp.pojo.Product;
import com.annp.service.CategoryService;
import com.annp.service.CategorySubService;
import com.annp.service.ProductService;
import com.annp.utils.Utils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategorySubService categorySubService;

    @ModelAttribute
    public void commonAttributes(Model model, HttpSession session) {
        List<Category> cates = this.categoryService.getCategories();
        List<CategorySub> categorySub = this.categorySubService.getCategorySub();
        model.addAttribute("categories", cates);
        model.addAttribute("categorySub", categorySub);
        model.addAttribute("cartStats", Utils.cartStats((Map<Integer, Cart>) session.getAttribute("cart")));
    }

    @GetMapping(path = "/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        List<Product> products = this.productService.getProducts(params);
        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping(path = "/products/{productId}")
    public String details(Model model,
                          @PathVariable(value = "productId") int id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "product-detail";
    }

    @GetMapping(path = "/cart")
    public String cart(Model model, HttpSession session) {
        model.addAttribute("carts", (Map<Integer, Cart>) session.getAttribute("cart"));
        return "cart";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
