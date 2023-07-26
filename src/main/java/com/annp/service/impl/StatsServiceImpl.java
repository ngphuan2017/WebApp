package com.annp.service.impl;

import com.annp.repository.StatsRepository;
import com.annp.service.StatsService;
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
    public List<Object[]> statsRevenue(Date fromDate, Date toDate) {
        return this.statsRepo.statsRevenue(fromDate, toDate);
    }
}
