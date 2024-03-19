/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.service;

import com.annp.pojo.District;
import java.util.List;

/**
 *
 * @author phuan
 */
public interface DistrictService {
    List<District> getDistrict();
    List<District> getDistrictsByCityId(int id);
}
