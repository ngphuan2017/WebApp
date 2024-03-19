/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.controllers;

import com.annp.pojo.District;
import com.annp.pojo.UserLevels;
import com.annp.pojo.Ward;
import com.annp.service.DistrictService;
import com.annp.service.UserLevelsService;
import com.annp.service.WardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author phuan
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {
    
    @Autowired
    private UserLevelsService userLevelsService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private WardService wardService;
    
    @GetMapping("/users/level/{userExp}")
    public ResponseEntity<Object> aboutProductView(@PathVariable(value = "userExp") int exp) {
        UserLevels level = this.userLevelsService.getUserLevelByExp(exp);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("level", level);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
    
    @GetMapping("/users/register/city/{cityId}")
    public ResponseEntity<Object> getListDistrict(@PathVariable(value = "cityId") int id) {
        List<District> districts = this.districtService.getDistrictsByCityId(id);
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }
    
    @GetMapping("/users/register/district/{districtId}")
    public ResponseEntity<Object> getListWard(@PathVariable(value = "districtId") int id) {
        List<Ward> wards = this.wardService.getWardsByDistrictId(id);
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }
    
}
