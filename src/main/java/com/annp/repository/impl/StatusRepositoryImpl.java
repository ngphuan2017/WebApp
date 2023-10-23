/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Status;
import com.annp.repository.StatusRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
public class StatusRepositoryImpl implements StatusRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Status> getStatus() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Status");
        return q.getResultList();
    }

    @Override
    public List<Status> getStatus(String columname) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Status> query = builder.createQuery(Status.class);
            Root root = query.from(Status.class);
            query.select(root);

            if (!columname.isEmpty()) {
                Predicate p = builder.equal(root.get("columnname"), columname);
                query.where(p);
            }
            query.orderBy(builder.asc(root.get("id")));

            Query q = session.createQuery(query);

            List<Status> status = q.getResultList();
            return status != null && !status.isEmpty() ? status : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Status getStatusById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Status.class, id);
    }

}
