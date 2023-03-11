/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public interface StatsRepository {
    List<Object[]> statsCategory();
    List<Object[]> statsRevenue(Date fromDate, Date toDate);
}
