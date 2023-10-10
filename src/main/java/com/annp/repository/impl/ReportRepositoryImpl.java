/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Report;
import com.annp.repository.ReportRepository;
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
public class ReportRepositoryImpl implements ReportRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Report> getReports() {
        try {
            Session s = factory.getObject().getCurrentSession();
            Query q = s.createQuery("From Report");
            return q.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
