/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Category;
import com.annp.pojo.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author admin
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model){
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Laptop"));
        categories.add(new Category(2, "Mobile"));
        categories.add(new Category(2, "PC"));
        model.addAttribute("categories", categories);
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Dell", "", 7000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        products.add(new Product(2, "MacBook", "", 6000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        products.add(new Product(3, "Asus", "", 5000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        products.add(new Product(4, "Lenovo", "", 4000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        products.add(new Product(5, "Acer", "", 3000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        products.add(new Product(6, "LG", "", 2000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        products.add(new Product(7, "HP", "", 1000000l, "", "https://cdn.tgdd.vn/Products/Images/44/264354/dell-gaming-g15-5511-i5-70266676-290322-110237-600x600.jpg"));
        model.addAttribute("products", products);
        return "index";
    }
}
