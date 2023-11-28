/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Promotion;
import com.annp.pojo.Status;
import com.annp.repository.PromotionRepository;
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
public class PromotionRepositoryImpl implements PromotionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Promotion> getPromotion(Map<String, String> params, int start, int limit) {
        try {
            Session s = factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Promotion> q = b.createQuery(Promotion.class);
            Root root = q.from(Promotion.class);
            q.select(root);

            if (params != null) {
                List<Predicate> predicates = new ArrayList<>();
                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty()) {
                    Predicate p = b.like(root.get("note"),
                            String.format("%%%s%%", kw));
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
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deletePromotion(int id) {
        Promotion p = this.getPromotionById(id);
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(p);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public Promotion getPromotionById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Promotion.class, id);
    }

    @Override
    public boolean updatePromotion(Promotion promotion) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            session.update(promotion);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Promotion> getPromotions() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Promotion");
        return q.getResultList();
    }

    @Override
    public List<Promotion> getPromotions(Status status) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Promotion> query = builder.createQuery(Promotion.class);
            Root root = query.from(Promotion.class);
            query = query.select(root);

            Predicate p = builder.equal(root.get("type"), status);
            query = query.where(p);
            query = query.orderBy(builder.asc(root.get("percentpage")));

            Query q = session.createQuery(query);

            List<Promotion> orders = q.getResultList();
            return orders != null && !orders.isEmpty() ? orders : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
