/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Notification;
import com.annp.pojo.Users;
import com.annp.repository.NotificationRepository;
import java.util.ArrayList;
import java.util.List;
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
public class NotificationRepositoryImpl implements NotificationRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Notification> getNotificationsByUserId(Users userId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
            Root root = query.from(Notification.class);
            query = query.select(root);

            Predicate p = builder.equal(root.get("userId"), userId);
            query = query.where(p);
            query = query.orderBy(builder.desc(root.get("createdDate")));

            Query q = session.createQuery(query);

            List<Notification> list = q.getResultList();
            return list != null && !list.isEmpty() ? list : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addNotification(Notification n) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(n);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
}