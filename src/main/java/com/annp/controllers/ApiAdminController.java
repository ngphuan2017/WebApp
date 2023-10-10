/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Product;
import com.annp.pojo.Users;
import com.annp.service.ProductService;
import com.annp.service.StatsService;
import com.annp.service.UserService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author phuan
 */
@RestController
@RequestMapping("/api")
public class ApiAdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StatsService statsService;

    @GetMapping("/admin/customer-management/{userId}")
    public ResponseEntity<Users> aboutAccountView(@PathVariable(value = "userId") int id) {
        Users user = this.userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/admin/order-management/{productId}")
    public ResponseEntity<Product> aboutProductView(@PathVariable(value = "productId") int id) {
        Product product = this.productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/admin/customer-management/deleted/{userId}")
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

    @GetMapping("/admin/download-pdf")
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
