/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Promotion;
import com.annp.pojo.Status;
import com.annp.service.PromotionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phuan
 */
@RestController
@RequestMapping("/api")
public class ApiWheelController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/wheel-of-forture")
    public ResponseEntity<Object> getAllWheel() {
        List<Promotion> promotion = this.promotionService.getPromotions(new Status(21));
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }
}
