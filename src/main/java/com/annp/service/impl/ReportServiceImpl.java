/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Report;
import com.annp.repository.ReportRepository;
import com.annp.service.ReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private ReportRepository reportRepository;
    
    @Override
    public List<Report> getReports() {
        return this.reportRepository.getReports();
    }
    
}
