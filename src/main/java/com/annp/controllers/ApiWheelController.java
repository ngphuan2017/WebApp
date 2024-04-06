/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.Notification;
import com.annp.pojo.Promotion;
import com.annp.pojo.Status;
import com.annp.pojo.Users;
import com.annp.service.NotificationService;
import com.annp.service.PromotionService;
import com.annp.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/wheel-of-forture")
    public ResponseEntity<Object> getAllWheel() {
        List<Promotion> promotion = this.promotionService.getPromotions(new Status(21));
        return new ResponseEntity<>(promotion, HttpStatus.OK);
    }

    @PostMapping("/wheel-of-forture/add/{userId}/{promotionId}")
    public ResponseEntity addWheel(@PathVariable(value = "userId") int userId, @PathVariable(value = "promotionId") int promotionId) {
        try {
            Users u = this.userService.getUserById(userId);
            Promotion p = this.promotionService.getPromotionById(promotionId);
            Notification n = new Notification();
            n.setId(0);
            n.setName("Lịch sử vòng quay");
            n.setDescription("🎁Bạn quay ra được " + p.getNote());
            n.setPromotionId(p);
            n.setUserId(u);
            if (this.notificationService.addNotification(n)) {
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
