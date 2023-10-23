/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Report;
import com.annp.pojo.Status;
import com.annp.repository.ReportRepository;
import com.annp.service.ReportService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Report> getReports() {
        return this.reportRepository.getReports();
    }

    @Override
    public List<Report> getReport(Map<String, String> params, int start, int limit) {
        return this.reportRepository.getReport(params, start, limit);
    }

    @Override
    public Report getReportById(int id) {
        return this.reportRepository.getReportById(id);
    }

    @Override
    public boolean deleteReport(int id) {
        return this.reportRepository.deleteReport(id);
    }

    @Override
    public List<Report> getReportByStatus(Status status) {
        return this.reportRepository.getReportByStatus(status);
    }

    @Override
    public boolean updateReport(Report report) {
        return this.reportRepository.updateReport(report);
    }

}
