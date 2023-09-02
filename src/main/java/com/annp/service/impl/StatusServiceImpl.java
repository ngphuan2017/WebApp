/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Status;
import com.annp.repository.StatusRepository;
import com.annp.service.StatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class StatusServiceImpl implements StatusService{

    @Autowired
    private StatusRepository statusRepository;
    
    @Override
    public List<Status> getStatus() {
        return this.statusRepository.getStatus();
    }
    
}
