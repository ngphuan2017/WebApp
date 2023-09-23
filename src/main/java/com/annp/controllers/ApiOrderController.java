/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phuan
 */
@RestController
@RequestMapping("/api")
public class ApiOrderController {

    @Autowired
    private OrdersService ordersService;

    @PutMapping("/orders/deleted/{orderdetailId}")
    public ResponseEntity deleteOrderDetail(@PathVariable(value = "orderdetailId") int id) {
        try {
            if (this.ordersService.deleteOrderDetail(id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
