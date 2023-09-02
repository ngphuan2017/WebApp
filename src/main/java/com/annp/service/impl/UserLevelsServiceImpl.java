/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.UserLevels;
import com.annp.repository.UserLevelsRepository;
import com.annp.service.UserLevelsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class UserLevelsServiceImpl implements UserLevelsService{

    @Autowired
    private UserLevelsRepository userLevelsRepository;
    
    @Override
    public List<UserLevels> getUserLevels() {
        return  this.userLevelsRepository.getUserLevels();
    }
    
}
