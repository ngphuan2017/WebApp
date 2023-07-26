package com.annp.repository;

import java.util.Date;
import java.util.List;

public interface StatsRepository {
    List<Object[]> statsCategory();

    List<Object[]> statsRevenue(Date fromDate, Date toDate);
}
