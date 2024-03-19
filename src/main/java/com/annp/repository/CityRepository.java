/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.City;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface CityRepository {
    City getCityById(int id);
    List<City> getCity();
}
