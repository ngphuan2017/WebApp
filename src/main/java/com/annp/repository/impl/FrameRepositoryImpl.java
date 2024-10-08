/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.repository.impl;

import com.annp.pojo.Frame;
import com.annp.repository.FrameRepository;
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
public class FrameRepositoryImpl implements FrameRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Frame> getFrames() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Frame");
        return q.getResultList();
    }

    @Override
    public Frame getFrameById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Frame.class, id);
    }
    
}
