package com.annp.service;

import java.util.Date;
import java.util.List;

public interface StatsService {
    List<Object[]> statsCategory();

    List<Object[]> statsRevenue(Date fromDate, Date toDate);
}
