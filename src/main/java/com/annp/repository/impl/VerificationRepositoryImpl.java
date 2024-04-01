/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Verification;
import com.annp.repository.VerificationRepository;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

}
