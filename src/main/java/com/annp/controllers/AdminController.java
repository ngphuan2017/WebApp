/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Product;
import com.annp.service.ProductService;
import com.annp.service.StatsService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author admin
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StatsService statsService;
    
    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("products", this.productService.getProducts(null));
    }
    
    @RequestMapping("/products")
    public String addProduct(Model model, 
            @ModelAttribute(value = "product") @Valid Product p,
            BindingResult rs) {
        if (rs.hasErrors())
            return "products";
        
        if (this.productService.addOrUpdateProduct(p) == true)
            return "redirect:/admin/products";
        else
            model.addAttribute("errMsg", "Something wrong!!!");
        
        return "products";
    }
    
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("product", new Product());
        
        return "products";
    }
    
    @GetMapping("/products/{productId}")
    public String updateProduct(Model model, @PathVariable(value = "productId") int id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "products";
    }
    
    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("cateStats", this.statsService.statsCategory());
        model.addAttribute("revenues", this.statsService.statsRevenue(null, null));
        return "stats";
    }
}
