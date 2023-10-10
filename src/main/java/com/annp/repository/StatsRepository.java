package com.annp.repository;

import java.util.Date;
import java.util.List;

public interface StatsRepository {
    List<Object[]> statsDay();
    List<Object[]> statsMonth();
    List<Object[]> statsYear();
    List<Object[]> statsCategory();
    List<Object[]> statsRevenue(Date fromDate, Date toDate);
}
