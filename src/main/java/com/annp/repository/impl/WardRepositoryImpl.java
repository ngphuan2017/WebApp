/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.District;
import com.annp.pojo.Ward;
import com.annp.repository.DistrictRepository;
import com.annp.repository.WardRepository;
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
public class WardRepositoryImpl implements WardRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private DistrictRepository districtRepository;
    
    @Override
    public List<Ward> getWard() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Ward");
        return q.getResultList();
    }

    @Override
    public List<Ward> getWardsByDistrictId(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Ward> query = builder.createQuery(Ward.class);
            Root root = query.from(Ward.class);
            query = query.select(root);
            
            District districtId = this.districtRepository.getDistrictById(id);
            
            Predicate p = builder.equal(root.get("districtId"), districtId);
            query = query.where(p);

            query = query.orderBy(builder.asc(root.get("id")));

            Query q = session.createQuery(query);

            List<Ward> wards = q.getResultList();
            return wards != null && !wards.isEmpty() ? wards : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
