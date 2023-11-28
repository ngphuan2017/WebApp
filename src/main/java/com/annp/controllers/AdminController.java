/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.dto.PaginatesDto;
import com.annp.pojo.Category;
import com.annp.pojo.CategorySub;
import com.annp.pojo.OrderDetail;
import com.annp.pojo.Product;
import com.annp.pojo.Promotion;
import com.annp.pojo.Report;
import com.annp.pojo.Status;
import com.annp.pojo.UserLevels;
import com.annp.pojo.Users;
import com.annp.service.CategoryService;
import com.annp.service.CategorySubService;
import com.annp.service.OrdersService;
import com.annp.service.PaginatesService;
import com.annp.service.ProductService;
import com.annp.service.PromotionService;
import com.annp.service.ReportService;
import com.annp.service.StatsService;
import com.annp.service.StatusService;
import com.annp.service.UserLevelsService;
import com.annp.service.UserService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class AdminController {

    @Autowired
    private StatusService statusService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private OrdersService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserLevelsService userLevelsService;
    @Autowired
    private CategorySubService categorySubService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private PaginatesService paginatesService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("reportbar", this.reportService.getReportByStatus(new Status(14)).size());
        model.addAttribute("orderDetailbar", this.orderService.getOrderDetailByStatus(new Status(9)).size());
        model.addAttribute("orderDetailStatus", this.statusService.getStatus("ORDERDETAILSTATUS"));
    }

    @GetMapping("/admin")
    public String indexAdmin(Model model) {
        model.addAttribute("cateStats", !this.statsService.statsCategory().isEmpty() ? this.statsService.statsCategory() : null);
        model.addAttribute("revenues", !this.statsService.statsRevenue().isEmpty() ? this.statsService.statsRevenue() : null);
        model.addAttribute("dayStats", !this.statsService.statsDay().isEmpty() ? this.statsService.statsDay() : null);
        model.addAttribute("yearStats", !this.statsService.statsYear().isEmpty() ? this.statsService.statsYear() : null);
        model.addAttribute("sidebar", "admin");
        return "admin";
    }

    @GetMapping("/admin/customer-management")
    public String adminAccount(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 25; //Số tài khoản 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.userService.getUsers(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<Users> users = this.userService.getUsers(params, paginates.getStart(), paginates.getLimit());
        model.addAttribute("users", users);
        List<UserLevels> listUserLevelses = this.userLevelsService.getUserLevels();
        model.addAttribute("listUserLevels", listUserLevelses);
        model.addAttribute("sidebar", "customer");
        return "admin-account";
    }

    @GetMapping("/admin/order-management")
    public String adminOrder(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 25; //Số đơn hàng 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.orderService.getOrderDetails(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<OrderDetail> orderDetail = this.orderService.getOrderDetails(params, paginates.getStart(), paginates.getLimit());
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("sidebar", "order");
        return "admin-order";
    }

    @GetMapping("/admin/product-management")
    public String adminProducts(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 25; //Số sản phẩm 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.productService.getProducts(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<Product> products = this.productService.getProducts(params, paginates.getStart(), paginates.getLimit());
        model.addAttribute("products", products);
        model.addAttribute("sidebar", "product");
        return "admin-products";
    }

    @GetMapping("/admin/category-management")
    public String adminCategorySub(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 25; //Số danh mục 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.categorySubService.getCategorySub(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<CategorySub> categorys = this.categorySubService.getCategorySub(params, paginates.getStart(), paginates.getLimit());
        List<Category> listCategory = this.categoryService.getCategories();
        model.addAttribute("categorys", categorys);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("sidebar", "category");
        return "admin-category";
    }

    @GetMapping("/admin/promotion-management")
    public String adminPromotion(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 25; //Số danh mục 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.promotionService.getPromotion(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<Promotion> promotions = this.promotionService.getPromotion(params, paginates.getStart(), paginates.getLimit());
        model.addAttribute("promotions", promotions);
        model.addAttribute("sidebar", "promotion");
        return "admin-promotion";
    }

    @GetMapping("/admin/report-management")
    public String adminReport(Model model, HttpServletRequest request, @RequestParam Map<String, String> params) {
        int limit = 25; //Số danh mục 1 trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalData = this.reportService.getReport(params, 0, 0).size();
        PaginatesDto paginates = paginatesService.getInfoPaginates(page, limit, totalData);
        model.addAttribute("page", paginates);
        List<Report> reports = this.reportService.getReport(params, paginates.getStart(), paginates.getLimit());
        model.addAttribute("reports", reports);
        model.addAttribute("sidebar", "report");
        return "admin-report";
    }
//    chua xu ly
    @RequestMapping("/admin/products")
    public String addProduct(Model model,
            @ModelAttribute(value = "product") @Valid Product p,
            BindingResult rs) {
        if (rs.hasErrors()) {
            return "admin-products";
        }

        if (this.productService.addOrUpdateProduct(p) == true) {
            return "redirect:/admin/products";
        } else {
            model.addAttribute("errMsg", "Something wrong!!!");
        }

        return "admin-products";
    }

    @GetMapping("/admin/products/{productId}")
    public String updateProduct(Model model, @PathVariable(value = "productId") int id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "admin-products";
    }

}
