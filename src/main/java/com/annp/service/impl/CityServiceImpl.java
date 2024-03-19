/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.City;
import com.annp.repository.CityRepository;
import com.annp.service.CityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class CityServiceImpl implements CityService{
    
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getCity() {
        return this.cityRepository.getCity();
    }

}
