/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Category;
import com.annp.pojo.CategorySub;
import com.annp.pojo.OrderDetail;
import com.annp.pojo.Product;
import com.annp.pojo.ProductImages;
import com.annp.pojo.Promotion;
import com.annp.pojo.Report;
import com.annp.pojo.Role;
import com.annp.pojo.Status;
import com.annp.pojo.UserLevels;
import com.annp.pojo.Users;
import com.annp.service.CategorySubService;
import com.annp.service.OrdersService;
import com.annp.service.ProductService;
import com.annp.service.PromotionService;
import com.annp.service.ReportService;
import com.annp.service.StatsService;
import com.annp.service.StatusService;
import com.annp.service.UserLevelsService;
import com.annp.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author phuan
 */
@RestController
@RequestMapping("/admin/api")
public class ApiAdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private CategorySubService categorySubService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private StatsService statsService;
    @Autowired
    private UserLevelsService userLevelsService;

    @GetMapping("/customer-management/{userId}")
    public ResponseEntity<Users> aboutAccountView(@PathVariable(value = "userId") int id) {
        Users user = this.userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/users/level/{userExp}")
    public ResponseEntity<Object> aboutProductView(@PathVariable(value = "userExp") int exp) {
        UserLevels level = this.userLevelsService.getUserLevelByExp(exp);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("level", level);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping("/{view:(?:order|product|orders-detail)-management}/{productId}")
    public ResponseEntity<Object> aboutProductView(@PathVariable(value = "view") String view, @PathVariable(value = "productId") int id) {
        Product product = this.productService.getProductById(id);
        if ("product-management".equals(view)) {
            List<CategorySub> categorySub = this.categorySubService.getCategorySub();
            List<Promotion> promotion = this.promotionService.getPromotions();
            List<Status> status = this.statusService.getStatus("PRODUCTSTATUS");
            ProductImages img = this.productService.getImagesByProductId(product);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("product", product);
            responseMap.put("listCategorySub", categorySub);
            responseMap.put("listPromotion", promotion);
            responseMap.put("listStatus", status);
            responseMap.put("img", img);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/product-management/listed")
    public ResponseEntity<Object> addProduct() {
        List<CategorySub> categorySub = this.categorySubService.getCategorySub();
        List<Promotion> promotion = this.promotionService.getPromotions();
        List<Status> status = this.statusService.getStatus("PRODUCTSTATUS");
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("listCategorySub", categorySub);
        responseMap.put("listPromotion", promotion);
        responseMap.put("listStatus", status);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PutMapping("/customer-management/deleted/{userId}")
    public ResponseEntity deleteCustomer(@PathVariable(value = "userId") int id) {
        try {
            if (this.userService.deleteCustomer(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/customer-management/edited/{userId}")
    public ResponseEntity editCustomer(@PathVariable(value = "userId") int id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("fullname") String fullname,
            @RequestParam("gender") String gender,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("userstatus") String userstatus,
            @RequestParam(value = "userRole", required = false) String userRole) {
        try {
            Users u = this.userService.getUserById(id);
            if (file != null) {
                u.setFile(file);
            }
            u.setFullname(fullname);
            if (!gender.isEmpty()) {
                u.setGender(Integer.valueOf(gender));
            }
            u.setEmail(email);
            u.setPhone(phone);
            u.setAddress(address);
            u.setUserstatus(new Status(Integer.valueOf(userstatus)));
            if (userRole != null) {
                u.setUserRole(new Role(Integer.valueOf(userRole)));
            }
            u.setUpdatedDate(new Date());
            if (this.userService.updateProfileUser(u)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/orders-detail-management/updated/{orderDetailId}")
    public ResponseEntity updateOrder(@PathVariable(value = "orderDetailId") int id, @RequestBody Map<String, String> params, Authentication authentication) {
        try {
            OrderDetail od = this.ordersService.getOrderDetailById(id);
            Product p = this.productService.getProductById(od.getProductId().getId());
            Users u = this.userService.getUserByUsername(authentication.getName());
            od.setOrderstatus(new Status(Integer.valueOf(params.get("orderDetailStatus"))));
            od.setUpdatedDate(new Date());
            od.setUpdatedBy(u);
            switch (Integer.parseInt(params.get("orderDetailStatus"))) {
                case 10:
                    p.setQuantity(p.getQuantity() - od.getNumber());
                    p.setUnitsSold(p.getUnitsSold() + od.getNumber());
                    break;
                case 11:
                    u.setExp(u.getExp() + 30);
                    u.setWheel(u.getWheel() + 1);
                    break;
                case 13:
                    p.setQuantity(p.getQuantity() + od.getNumber());
                    p.setUnitsSold(p.getUnitsSold() - od.getNumber());
                    break;
                default:
                    break;
            }
            if (this.ordersService.updateOrderDetail(od) && this.productService.updateProduct(p)) {
                if (u.getNotification() == null || u.getNotification() == 0) {
                    u.setNotification(1);
                } else {
                    u.setNotification(u.getNotification() + 1);
                }
                this.userService.updateUser(u);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/orders-detail-management/deleted/{orderDetailId}")
    public ResponseEntity deleteOrder(@PathVariable(value = "orderDetailId") int id) {
        try {
            if (this.ordersService.deleteOrderDetail(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/product-management/deleted/{productId}")
    public ResponseEntity deleteProduct(@PathVariable(value = "productId") int id) {
        try {
            if (this.productService.deleteProduct(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product-management/edited/{productId}")
    public ResponseEntity editProduct(@PathVariable(value = "productId") int id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("quantity") String quantity,
            @RequestParam("categorysubId") String categorysubId,
            @RequestParam("productstatus") String productstatus,
            @RequestParam("discount") String discount) {
        try {
            Product p = this.productService.getProductById(id);
            if (file != null) {
                p.setFile(file);
            }
            p.setName(name);
            p.setPrice(Integer.valueOf(price));
            p.setQuantity(Integer.valueOf(quantity));
            p.setCategorysubId(new CategorySub(Integer.valueOf(categorysubId)));
            p.setProductstatus(new Status(Integer.valueOf(productstatus)));
            p.setDiscount(new Promotion(Integer.valueOf(discount)));
            p.setUpdatedDate(new Date());
            if (this.productService.addOrUpdateProduct(p)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product-management/added")
    public ResponseEntity addProduct(@RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("quantity") String quantity,
            @RequestParam("categorysubId") String categorysubId,
            @RequestParam("productstatus") String productstatus,
            @RequestParam("discount") String discount) {
        try {
            Product p = new Product();
            p.setId(0);
            p.setReviewCount(0);
            p.setAverageRating(BigDecimal.valueOf(0));
            p.setUnitsSold(0);
            p.setCreatedDate(new Date());
            if (file != null) {
                p.setFile(file);
            }
            p.setName(name);
            p.setPrice(Integer.valueOf(price));
            p.setQuantity(Integer.valueOf(quantity));
            p.setCategorysubId(new CategorySub(Integer.valueOf(categorysubId)));
            p.setProductstatus(new Status(Integer.valueOf(productstatus)));
            p.setDiscount(new Promotion(Integer.valueOf(discount)));
            if (this.productService.addOrUpdateProduct(p)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product-management/product-images/updated/{productId}")
    public ResponseEntity updateProductImages(@PathVariable(value = "productId") int id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "file1", required = false) MultipartFile file1,
            @RequestParam(value = "file2", required = false) MultipartFile file2,
            @RequestParam(value = "file3", required = false) MultipartFile file3,
            Authentication authentication) {
        try {
            Users u = this.userService.getUserByUsername(authentication.getName());
            Product p = this.productService.getProductById(id);
            ProductImages img = new ProductImages();
            if (this.productService.getImagesByProductId(p) == null) {
                img.setId(0);
                img.setCreatedDate(new Date());
                img.setProductId(p);
            } else {
                img = this.productService.getImagesByProductId(p);
                img.setUpdatedDate(new Date());
            }
            if (file != null) {
                p.setFile(file);
                p.setUpdatedDate(new Date());
            }
            if (file1 != null) {
                img.setFile1(file1);
            }
            if (file2 != null) {
                img.setFile2(file2);
            }
            if (file3 != null) {
                img.setFile3(file3);
            }
            
            img.setUpdatedBy(u);
            
            if (this.productService.addOrUpdateProduct(p) && this.productService.addOrUpdateProductImages(img)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/promotion-management/added")
    public ResponseEntity addPromotion(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("note") String note,
            @RequestParam("code") String code,
            @RequestParam("discount") String discount,
            @RequestParam("beginDate") String beginDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("type") String type,
            @RequestParam("quantity") String quantity,
            @RequestParam(value = "percentpage", required = false) String percentpage,
            @RequestParam(value = "levelVip", required = false) String levelVip) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Promotion p = new Promotion();
            p.setId(0);
            if (file != null) {
                p.setFile(file);
            }
            p.setNote(note);
            p.setCode(code);
            p.setDiscount(Integer.valueOf(discount));
            p.setBeginDate(dateFormat.parse(beginDate));
            p.setEndDate(dateFormat.parse(endDate));
            p.setType(new Status(Integer.valueOf(type)));
            p.setQuantity(Integer.valueOf(quantity));
            if (percentpage != null) {
                p.setPercentpage(Float.valueOf(percentpage));
            }
            p.setLevelVip(new UserLevels(levelVip != null ? Integer.parseInt(levelVip) : 0));
            p.setCreatedDate(new Date());
            if (this.promotionService.addPromotion(p)) {
                return new ResponseEntity(HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/promotion-management/edited/{promotionId}")
    public ResponseEntity updatePromotion(@PathVariable(value = "promotionId") int id, @RequestBody Map<String, String> params) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Promotion promotion = this.promotionService.getPromotionById(id);
            promotion.setNote(params.get("note"));
            promotion.setCode(params.get("code"));
            promotion.setDiscount(Integer.valueOf(params.get("discount")));
            promotion.setBeginDate(dateFormat.parse(params.get("beginDate")));
            promotion.setEndDate(dateFormat.parse(params.get("endDate")));
            promotion.setType(new Status(Integer.valueOf(params.get("type"))));
            promotion.setQuantity(Integer.valueOf(params.get("quantity")));
            promotion.setLevelVip(new UserLevels(params.get("levelVip") != null ? Integer.parseInt(params.get("levelVip")) : 0));
            if (this.promotionService.updatePromotion(promotion)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/promotion-management/deleted/{promotionId}")
    public ResponseEntity deletePromotion(@PathVariable(value = "promotionId") int id) {
        try {
            if (this.promotionService.deletePromotion(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/category-management/edited/{categorySubId}")
    public ResponseEntity updateCategorySub(@PathVariable(value = "categorySubId") int id, @RequestBody Map<String, String> params) {
        try {
            CategorySub categorySub = this.categorySubService.getCategorySubById(id);
            categorySub.setName(params.get("name"));
            categorySub.setCategoryId(new Category(Integer.valueOf(params.get("categoryId"))));
            categorySub.setUpdatedDate(new Date());
            if (this.categorySubService.updateCategorySub(categorySub)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/category-management/deleted/{categorySubId}")
    public ResponseEntity deleteCategorySub(@PathVariable(value = "categorySubId") int id) {
        try {
            if (this.categorySubService.deleteCategorySub(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/report-management/edited/{reportId}")
    public ResponseEntity updateReport(@PathVariable(value = "reportId") int id, @RequestBody Map<String, Integer> params) {
        try {
            Report r = this.reportService.getReportById(id);
            Users u = this.userService.getUserById(r.getUserid().getId());
            if (params.get("statusId") == 16) {
                u.setUserstatus(new Status(2));
                this.userService.updateUser(u);
            }
            r.setReportstatus(new Status(params.get("statusId")));
            if (this.reportService.updateReport(r)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/report-management/deleted/{reportId}")
    public ResponseEntity deleteReport(@PathVariable(value = "reportId") int id) {
        try {
            if (this.reportService.deleteReport(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<InputStreamResource> downloadPDF() {
        try {
            List<Object[]> statsMonth = this.statsService.statsMonth();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            String htmlContent = generateHTML(statsMonth); // Tạo HTML từ dữ liệu
            htmlContent = new String(htmlContent.getBytes(), StandardCharsets.UTF_8);

            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, true);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "attachment; filename*=UTF-8''download.pdf");

            return ResponseEntity.ok().headers(headers)
                    .body(new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    private String generateHTML(List<Object[]> statsMonth) {
        StringBuilder html = new StringBuilder();

        Date currentDate = new Date();
        // Định dạng thời gian hiện tại thành chuỗi "MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        String currentMonth = sdf.format(currentDate);
        // Tạo nội dung HTML từ dữ liệu statsMonth
        html.append("<html><body style='background: url(https://res.cloudinary.com/dkmug1913/image/upload/v1696666122/WebApp/bgpdf_exkavw.png) left top / cover no-repeat;'>");
        html.append("<h3 style='text-align: center; padding-top: 200px;'>BÁO CÁO DOANH THU THÁNG ").append(currentMonth).append("</h3>");
        html.append("<table border='1' cellpadding='5' style='margin: 0 auto;'>");
        html.append("<tr style='background: #E0F2E0; font-weight: 600;'><th>STT</th><th>THÁNG</th><th>SL DON HÀNG</th><th>SL SAN PHAM</th><th>DOANH THU</th></tr>");

        int stt = 1;
        Long sum = 0l;
        for (Object[] stat : statsMonth) {
            html.append("<tr style='text-align: center;'>");
            html.append("<td>").append(stt++).append("</td>");
            html.append("<td>").append(stat[0]).append("</td>"); // THÁNG
            html.append("<td>").append(stat[1]).append("</td>"); // ORDER_ID
            html.append("<td>").append(stat[2]).append("</td>"); // LOẠI HÓA ĐƠN
            html.append("<td style='text-align: right;'>").append(stat[3]).append("</td>"); // TIỀN HÀNG
            html.append("</tr>");
            sum += (Long) stat[3];
        }
        html.append("<tr style='text-align: center;'>");
        html.append("<th colspan='4'>Tông:</th>");
        html.append("<td style='text-align: right;'>").append(sum).append("</td>"); // DOANH THU
        html.append("</tr>");

        html.append("</table>");
        html.append("</body></html>");

        return html.toString();
    }

}
