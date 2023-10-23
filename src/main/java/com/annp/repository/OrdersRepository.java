/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.OrderDetail;
import com.annp.pojo.Orders;
import com.annp.pojo.Status;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public interface OrdersRepository {
    public List<OrderDetail> getOrderDetails(Map<String, String> params, int start, int limit);
    public List<OrderDetail> getOrderDetailByStatus(Status status);
    public Orders getOrderById(int id);
    public List<Orders> getOrderByUserId(int id);
    public OrderDetail getOrderDetailById(int id);
    public List<OrderDetail> getOrderDetailByOrderId(int id);
    public boolean updateOrderDetail(OrderDetail orderDetail);
    public boolean deleteOrderDetail(int id);
}
