/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Verification;
import com.annp.repository.VerificationRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class VerificationRepositoryImpl implements VerificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean addOrUpdateOtpCode(Verification verification) {
        Session session = this.factory.getObject().getCurrentSession();
        try {
            if (verification.getId() != 0) {
                session.update(verification);
            } else {
                session.save(verification);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Verification getVerificationByEmail(String email) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Verification> q = b.createQuery(Verification.class);
        Root root = q.from(Verification.class);
        q.select(root);
        q.where(b.equal(root.get("propersion"), email));
        Query query = s.createQuery(q);
        try {
            Verification verification = (Verification) query.getSingleResult();
            return verification;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    @Override
    public Verification getVerificationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Verification.class, id);
    }

    @Override
    public boolean deleteVerification(int id) {
        Verification v = this.getVerificationById(id);
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.delete(v);
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    @Override
    public List<Verification> getVerificationsByThanDay(int day) {
        try {
            // Tính toán ngày cách đây `days` ngày
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -day);
            Date targetDate = cal.getTime();

            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Verification> query = builder.createQuery(Verification.class);
            Root root = query.from(Verification.class);
            query = query.select(root);

            // So sánh ngày tạo <= targetDate
            Predicate p = builder.lessThanOrEqualTo(root.get("generatedTime"), targetDate);
            query = query.where(p);

            Query q = session.createQuery(query);
            List<Verification> list = q.getResultList();
            return list != null && !list.isEmpty() ? list : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

}
