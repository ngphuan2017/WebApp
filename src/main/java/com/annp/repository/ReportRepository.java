/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.Report;
import com.annp.pojo.Status;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public interface ReportRepository {

    Report getReportById(int id);
    
    boolean updateReport(Report report);

    boolean deleteReport(int id);

    public List<Report> getReports();

    List<Report> getReport(Map<String, String> params, int start, int limit);

    public List<Report> getReportByStatus(Status status);
}
