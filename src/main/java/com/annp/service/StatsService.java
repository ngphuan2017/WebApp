package com.annp.service;

import java.util.Date;
import java.util.List;

public interface StatsService {
    List<Object[]> statsDay();
    List<Object[]> statsMonth();
    List<Object[]> statsYear();
    List<Object[]> statsCategory();
    List<Object[]> statsRevenue();
}
