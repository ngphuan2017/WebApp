/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author phuan
 */
@Controller
public class PaymentController {

    @GetMapping(path = "/vnpay/payment")
    public String payment(Model model, @RequestParam Map<String, String> params) throws ParseException {

        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        Double amount = Double.parseDouble(params.get("vnp_Amount")) / 100;
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMddHHmmss");
        Date paymentDate = sdfInput.parse(params.get("vnp_PayDate"));
        SimpleDateFormat sdfOutput = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String formattedDate = sdfOutput.format(paymentDate);

        model.addAttribute("vnp_TxnRef", params.get("vnp_TxnRef"));
        model.addAttribute("vnp_OrderInfo", params.get("vnp_OrderInfo"));
        model.addAttribute("vnp_Amount", decimalFormat.format(amount));
        model.addAttribute("vnp_ResponseCode", params.get("vnp_ResponseCode"));
        model.addAttribute("vnp_TransactionNo", params.get("vnp_TransactionNo"));
        model.addAttribute("vnp_BankCode", params.get("vnp_BankCode"));
        model.addAttribute("vnp_PayDate", formattedDate);
        return "vn-payment";

    }

}
