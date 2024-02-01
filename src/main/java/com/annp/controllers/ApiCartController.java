/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.configs.VNPayConfig;
import com.annp.pojo.*;
import com.annp.service.ProductService;
import com.annp.service.PromotionService;
import com.annp.service.UserService;
import com.annp.utils.Utils;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiCartController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private Environment env;

    @PostMapping(value = "/cart")
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody Cart c, HttpSession session) {

        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        if (cart.containsKey(c.getId()) == true) {
            Cart t = cart.get(c.getId());
            t.setQuantity(t.getQuantity() + c.getQuantity());
        } else {
            cart.put(c.getId(), c);
        }

        session.setAttribute("cart", cart);

        return new ResponseEntity<>(Utils.cartStats(cart), HttpStatus.OK);
    }

    @PutMapping(path = "/cart/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateItemCart(@PathVariable(value = "productId") int id,
            @RequestBody Map<String, Integer> params, HttpSession session) {
        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(id)) {
            Cart c = cart.get(id);
            c.setQuantity(params.get("quantity"));
        }

        session.setAttribute("cart", cart);

        return new ResponseEntity<>(Utils.cartStats(cart), HttpStatus.OK);
    }

    @DeleteMapping(path = "/cart/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> deleteItemCart(@PathVariable(value = "productId") int id, HttpSession session) {
        Map<Integer, Cart> cart = (Map<Integer, Cart>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(id)) {
            cart.remove(id);
        }

        session.setAttribute("cart", cart);

        return new ResponseEntity<>(Utils.cartStats(cart), HttpStatus.OK);
    }

    @GetMapping("/cart/voucher")
    public ResponseEntity<Object> checkVoucherCode() {
        List<Promotion> promotions = new ArrayList<>();
        promotions.addAll(this.promotionService.getPromotions(new Status(19)));
        promotions.addAll(this.promotionService.getPromotions(new Status(21)));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("promotions", promotions);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity pay(HttpSession session, Authentication authentication, @RequestBody Map<String, String> params, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int option = Integer.parseInt(params.get("optionPay"));
        Map<String, Cart> cart = (Map<String, Cart>) session.getAttribute("cart");
        Integer amount = 0;
        if (cart != null) {
            for (Cart c : cart.values()) {
                amount += c.getQuantity() * c.getPrice();
            }
        }
        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        Users user = this.userService.getUserByUsername(authentication.getName());

        switch (option) {
            case 1:
                if (this.productService.addReceipt((Map<String, Cart>) session.getAttribute("cart"))) {
                    session.removeAttribute("cart");
                    sendOrderToEmail(user.getFullname(), user.getEmail(), baseUrl, cart, amount);
                    return new ResponseEntity(HttpStatus.OK);
                }
                break;
            case 2:
                if (this.productService.addReceiptPaid((Map<String, Cart>) session.getAttribute("cart"))) {
                    session.removeAttribute("cart");
                    sendOrderToEmail(user.getFullname(), user.getEmail(), baseUrl, cart, amount);
                    HttpHeaders headers = new HttpHeaders();
                    String urlPayment = doPost(req, resp, amount);
                    headers.add("Location", urlPayment);
                    return new ResponseEntity(headers, HttpStatus.OK);
                }
                break;
            case 3:
                if (this.productService.addReceiptPaid((Map<String, Cart>) session.getAttribute("cart"))) {
                    session.removeAttribute("cart");
                    return new ResponseEntity(HttpStatus.OK);
                }
                break;
            case 4:
                if (this.productService.addReceiptPaid((Map<String, Cart>) session.getAttribute("cart"))) {
                    session.removeAttribute("cart");
                    return new ResponseEntity(HttpStatus.OK);
                }
                break;
            default:
                break;
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    private boolean sendOrderToEmail(String fullname, String email, String baseUrl, Map<String, Cart> cart, Integer amount) {
        try {
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            String date = formatter.format(cld.getTime());
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHostName(env.getProperty("spring.mail.host")); // SMTP server
            htmlEmail.setSmtpPort(Integer.parseInt(env.getProperty("spring.mail.port"))); // Port
            htmlEmail.setAuthenticator(new DefaultAuthenticator(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"))); // Email và mật khẩu
            htmlEmail.setStartTLSEnabled(true); // Bật TLS

            htmlEmail.setFrom(env.getProperty("spring.mail.username"), "PhuAnShop");
            htmlEmail.setCharset("UTF-8");
            htmlEmail.setSubject("Thông tin đơn hàng đã mua tại Phú An Shop");
            String htmlMessage = "<html><body style='margin-right: auto; margin-left: auto; padding-left: 15px; padding-right: 15px; width: 100%; font-size:16px;'>";
            htmlMessage += "<p align='center'><a href='" + baseUrl + "'><img src='https://res.cloudinary.com/dkmug1913/image/upload/v1687075830/WebApp/logo_km2dfc.png' alt='Phú An Shop' /></a></p>";
            htmlMessage += "<p>Xin chào <span style='color: #ee4d2d'>" + fullname + "</span>,</p>";
            htmlMessage += "<table bgcolor='#fff' cellpadding='0' cellspacing='0' border='0' align='center'><thead><tr><th colspan='2' style='padding-bottom: 15px;'>THÔNG TIN ĐƠN HÀNG</th></tr></thead><tbody>";
            if (cart != null) {
                for (Cart c : cart.values()) {
                    htmlMessage += "<tr><td>Sản phẩm: </td><td style='color: gray;'>"+ c.getName() +"</td></tr>";
                    htmlMessage += "<tr><td>Đơn giá: </td><td style='color: gray;'>"+ decimalFormat.format(c.getPrice()) +" VNĐ</td></tr>";
                    htmlMessage += "<tr><td>Số lượng: </td><td style='color: gray;'>"+ c.getQuantity() +"</td></tr>";
                    htmlMessage += "<tr><td>Số tiền: </td><td style='color: gray;'>"+ decimalFormat.format((long) c.getPrice() *c.getQuantity()) +" VNĐ</td></tr>";
                    htmlMessage += "<tr><td>Ngày đặt hàng: </td><td style='color: gray;'>"+ date +"</td></tr>";
                    htmlMessage += "<tr><td colspan='2'><hr/></td></tr>";
                }
            }
            htmlMessage += "<tr><td>Tổng tiền: </td><td style='color: #FA8072;'>"+ decimalFormat.format(amount) +" VNĐ</td></tr>";
            htmlMessage += "<tr><td>Voucher từ Shop: </td><td style='color: #FA8072;'>0 VNĐ</td></tr>";
            htmlMessage += "<tr><td>Phí vận chuyển: </td><td style='color: #FA8072;'>0 VNĐ</td></tr>";
            htmlMessage += "<tr><td>Tổng thanh toán: </td><td style='color: red;'>"+ decimalFormat.format(amount) +" VNĐ</td></tr>";
            htmlMessage += "</tbody></table>";
            htmlMessage += "<p align='center' style='padding: 10px;'><a href='" + baseUrl + "' align='center' ";
            htmlMessage += "style='padding: 8px 30px; border-radius: 3px; background-color: #ee4d2d; color:#fff; text-decoration: none;'>Đi đến Shop</a></p>";
            htmlMessage += "<p>Bạn có thể gửi yêu cầu trả hàng cho email: phuanshop2023@gmail.com trong vòng 7 ngày kể từ khi nhận được email này.</p>";
            htmlMessage += "<p>Chúc bạn luôn có những trải nghiệm tuyệt vời khi mua sắm tại PhuAnShop.</p>";
            htmlMessage += "<p>Lưu ý: Shop sẽ từ chối hỗ trợ các khiếu nại về Trả hàng/Hoàn tiền sau 7 ngày kể từ khi nhận được email này.</p>";
            htmlMessage += "<p>Trân trọng,</p>";
            htmlMessage += "<p>PhuAnShop</p>";
            htmlMessage += "</body></html>";
            htmlEmail.setHtmlMsg(htmlMessage);
            htmlEmail.addTo(email);

            htmlEmail.send();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    protected String doPost(HttpServletRequest req, HttpServletResponse resp, Integer amount) throws ServletException, IOException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        amount *= 100;

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getIpAddress(req);

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }
}
