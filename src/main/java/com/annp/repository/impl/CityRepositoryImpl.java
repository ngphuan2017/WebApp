/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.City;
import com.annp.repository.CityRepository;
import java.util.List;
import javax.persistence.Query;
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
public class CityRepositoryImpl implements CityRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<City> getCity() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From City");
        return q.getResultList();
    }
    
}
