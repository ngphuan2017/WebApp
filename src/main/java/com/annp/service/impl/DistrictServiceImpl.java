/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.District;
import com.annp.repository.DistrictRepository;
import com.annp.service.DistrictService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class DistrictServiceImpl implements DistrictService{

    @Autowired
    private DistrictRepository districtRepository;
    
    @Override
    public List<District> getDistrict() {
        return this.districtRepository.getDistrict();
    }

    @Override
    public List<District> getDistrictsByCityId(int id) {
        return this.districtRepository.getDistrictsByCityId(id);
    }
    
}
