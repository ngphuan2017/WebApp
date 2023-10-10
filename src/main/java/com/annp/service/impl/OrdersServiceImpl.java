/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.OrderDetail;
import com.annp.pojo.Orders;
import com.annp.pojo.Status;
import com.annp.repository.OrdersRepository;
import com.annp.service.OrdersService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class OrdersServiceImpl implements OrdersService{

    @Autowired
    private OrdersRepository ordersRepository;
    
    @Override
    public List<Orders> getOrderByUserId(int id) {
        return this.ordersRepository.getOrderByUserId(id);
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(int id) {
        return this.ordersRepository.getOrderDetailByOrderId(id);
    }

    @Override
    public Orders getOrderById(int id) {
        return this.ordersRepository.getOrderById(id);
    }

    @Override
    public OrderDetail getOrderDetailById(int id) {
        return this.ordersRepository.getOrderDetailById(id);
    }

    @Override
    public boolean deleteOrderDetail(int id) {
        return this.ordersRepository.deleteOrderDetail(id);
    }

    @Override
    public List<OrderDetail> getOrderDetailByStatus(Status status) {
        return this.ordersRepository.getOrderDetailByStatus(status);
    }

    @Override
    public List<OrderDetail> getOrderDetails(Map<String, String> params, int start, int limit) {
        return this.ordersRepository.getOrderDetails(params, start, limit);
    }
    
}
