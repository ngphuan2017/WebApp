/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Frame;
import com.annp.repository.FrameRepository;
import com.annp.service.FrameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class FrameServiceImpl implements FrameService{
    
    @Autowired
    private FrameRepository frameRepository;

    @Override
    public List<Frame> getFrames() {
        return this.frameRepository.getFrames();
    }

    @Override
    public Frame getFrameById(int id) {
        return this.frameRepository.getFrameById(id);
    }
    
}
