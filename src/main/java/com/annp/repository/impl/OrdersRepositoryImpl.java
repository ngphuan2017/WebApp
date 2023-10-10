/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.OrderDetail;
import com.annp.pojo.Orders;
import com.annp.pojo.Status;
import com.annp.pojo.Users;
import com.annp.repository.OrdersRepository;
import com.annp.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author phuan
 */
@Repository
@Transactional
public class OrdersRepositoryImpl implements OrdersRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Orders getOrderById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(Orders.class, id);
    }

    @Override
    public OrderDetail getOrderDetailById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(OrderDetail.class, id);
    }

    @Override
    public List<Orders> getOrderByUserId(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Orders> query = builder.createQuery(Orders.class);
            Root root = query.from(Orders.class);
            query = query.select(root);

            Users user = this.userRepository.getUserById(id);
            Predicate p = builder.equal(root.get("userid"), user);
            query = query.where(p);
            query = query.orderBy(builder.desc(root.get("id")));

            Query q = session.createQuery(query);

            List<Orders> orders = q.getResultList();
            return orders != null && !orders.isEmpty() ? orders : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderId(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderDetail> query = builder.createQuery(OrderDetail.class);
            Root root = query.from(OrderDetail.class);
            query = query.select(root);

            Orders order = this.getOrderById(id);
            Predicate p = builder.equal(root.get("orderId"), order);
            query = query.where(p);
            query = query.orderBy(builder.desc(root.get("id")));

            Query q = session.createQuery(query);

            List<OrderDetail> orders = q.getResultList();
            return orders != null && !orders.isEmpty() ? orders : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteOrderDetail(int id) {
        OrderDetail od = this.getOrderDetailById(id);
        od.setOrderstatus(new Status(12));
        Orders o = this.getOrderById(od.getOrderId().getId());
        o.setAmount(o.getAmount() - od.getNumber() * od.getPrice());
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(od);
            s.update(o);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailByStatus(Status status) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<OrderDetail> query = builder.createQuery(OrderDetail.class);
            Root root = query.from(OrderDetail.class);
            query = query.select(root);

            Predicate p = builder.equal(root.get("orderstatus"), status);
            query = query.where(p);
            query = query.orderBy(builder.desc(root.get("id")));

            Query q = session.createQuery(query);

            List<OrderDetail> orders = q.getResultList();
            return orders != null && !orders.isEmpty() ? orders : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderDetail> getOrderDetails(Map<String, String> params, int start, int limit) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<OrderDetail> q = b.createQuery(OrderDetail.class);
            Root root = q.from(OrderDetail.class);
            q.select(root);

            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();
                String orderId = params.get("orderId");
                if (orderId != null && orderId.matches("^\\d+$")) {
                    Predicate p = b.equal(root.get("orderId"), Integer.parseInt(orderId));
                    predicates.add(p);
                }
                q.where(predicates.toArray(Predicate[]::new));
            }

            q.orderBy(b.desc(root.get("id")));
            Query query = s.createQuery(q);
            if (start > 0 && limit > 0) {
                query.setFirstResult(start - 1); // Vị trí bắt đầu
                query.setMaxResults(limit); // Số lượng kết quả trả về
            }
            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
}
