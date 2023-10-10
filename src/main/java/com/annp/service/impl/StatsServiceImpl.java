package com.annp.service.impl;

import com.annp.repository.StatsRepository;
import com.annp.service.StatsService;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> statsCategory() {
        return this.statsRepo.statsCategory();
    }

    @Override
    public List<Object[]> statsRevenue() {
        Date fromDate = calculateDateBeforeMonths(new Date(), 8);
        return this.statsRepo.statsRevenue(fromDate, new Date());
    }

    private Date calculateDateBeforeMonths(Date currentDate, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }

    @Override
    public List<Object[]> statsDay() {
        return this.statsRepo.statsDay();
    }

    @Override
    public List<Object[]> statsMonth() {
        return this.statsRepo.statsMonth();
    }

    @Override
    public List<Object[]> statsYear() {
        return this.statsRepo.statsYear();
    }

}
