/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.dto.PaginatesDto;
import com.annp.pojo.Cart;
import com.annp.pojo.Category;
import com.annp.pojo.CategorySub;
import com.annp.pojo.Product;
import com.annp.pojo.UserLevels;
import com.annp.service.CategoryService;
import com.annp.service.CategorySubService;
import com.annp.service.PaginatesService;
import com.annp.service.ProductService;
import com.annp.service.UserLevelsService;
import com.annp.utils.Utils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private UserLevelsService userLevelsService;
    @Autowired
    private PaginatesService paginatesService;

    @ModelAttribute
    public void commonAttributes(Model model, HttpSession session) {
        List<Category> cates = this.categoryService.getCategories();
        List<CategorySub> categorySub = this.categorySubService.getCategorySub();
        model.addAttribute("categories", cates);
        model.addAttribute("categorySub", categorySub);
        model.addAttribute("cartStats", Utils.cartStats((Map<Integer, Cart>) session.getAttribute("cart")));
        List<UserLevels> listUserLevelses = this.userLevelsService.getUserLevels();
        model.addAttribute("listCmtUserLevels", listUserLevelses);
    }

    @GetMapping(path = "/")
    public String index(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 18; //Số sản phẩm 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.productService.getProducts(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<Product> products = this.productService.getProducts(params, paginates.getStart(), paginates.getLimit());
        model.addAttribute("products", products);

        return "index";
    }
    
    @GetMapping(path = "/about")
    public String about() {
        return "about";
    }
    
    @GetMapping(path = "/maintenance")
    public String maintenance(Model model) {
        return "maintenance";
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

}
