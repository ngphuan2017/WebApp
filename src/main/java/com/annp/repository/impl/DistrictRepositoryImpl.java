/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.City;
import com.annp.pojo.District;
import com.annp.repository.CityRepository;
import com.annp.repository.DistrictRepository;
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
public class DistrictRepositoryImpl implements DistrictRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<District> getDistrict() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From District");
        return q.getResultList();
    }

    @Override
    public List<District> getDistrictsByCityId(int id) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<District> query = builder.createQuery(District.class);
            Root root = query.from(District.class);
            query = query.select(root);
            
            City cityId = this.cityRepository.getCityById(id);
            
            Predicate p = builder.equal(root.get("cityId"), cityId);
            query = query.where(p);

            query = query.orderBy(builder.asc(root.get("id")));

            Query q = session.createQuery(query);

            List<District> districts = q.getResultList();
            return districts != null && !districts.isEmpty() ? districts : new ArrayList<>();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public District getDistrictById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        return session.get(District.class, id);
    }

}
