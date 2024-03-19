/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.UserLevels;
import com.annp.repository.UserLevelsRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class UserLevelsRepositoryImpl implements UserLevelsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<UserLevels> getUserLevels() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From UserLevels");
        return q.getResultList();
    }

    @Override
    public UserLevels getUserLevelByExp(int exp) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<UserLevels> q = b.createQuery(UserLevels.class);
        Root<UserLevels> root = q.from(UserLevels.class);
        q.select(root);
        q.where(b.lessThanOrEqualTo(root.get("requiredExp"), exp));
        q.orderBy(b.desc(root.get("requiredExp")));
        Query query = s.createQuery(q);
        query.setMaxResults(1);
        try {
            UserLevels level = (UserLevels) query.getSingleResult();
            return level;
        } catch (Exception e) {
            return null;
        }
    }

}
