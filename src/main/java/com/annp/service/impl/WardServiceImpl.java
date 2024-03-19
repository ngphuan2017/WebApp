/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Ward;
import com.annp.repository.WardRepository;
import com.annp.service.WardService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class WardServiceImpl implements WardService{

    @Autowired
    private WardRepository wardRepository;
    
    @Override
    public List<Ward> getWard() {
        return this.wardRepository.getWard();
    }

    @Override
    public List<Ward> getWardsByDistrictId(int id) {
        return this.wardRepository.getWardsByDistrictId(id);
    }
    
}
