/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.pojo.OrderDetail;
import com.annp.pojo.Orders;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface OrdersService {
    public Orders getOrderById(int id);
    public List<Orders> getOrderByUserId(int id);
    public OrderDetail getOrderDetailById(int id);
    public List<OrderDetail> getOrderDetailByOrderId(int id);
    public boolean deleteOrderDetail(int id);
}
