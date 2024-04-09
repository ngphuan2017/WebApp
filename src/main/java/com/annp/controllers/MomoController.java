/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
public class MomoController {

    @GetMapping("/momo/paymentCallback")
    public String paymentCallback(Model model, @RequestParam Map<String, String> params) {

        long responseTimeInMillis = Long.parseLong(params.get("responseTime"));
        Instant instant = Instant.ofEpochMilli(responseTimeInMillis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedDateTime = localDateTime.format(formatter);
        model.addAttribute("orderId", params.get("orderId"));
        model.addAttribute("amount", params.get("amount"));
        model.addAttribute("orderInfo", params.get("orderInfo"));
        model.addAttribute("resultCode", params.get("resultCode"));
        model.addAttribute("message", params.get("message"));
        model.addAttribute("payType", params.get("payType"));
        model.addAttribute("responseTime", formattedDateTime);
        return "momo-payment";

    }

}
