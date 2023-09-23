/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.dto.PaginatesDto;
import com.annp.service.PaginatesService;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class PaginatesServiceImpl implements PaginatesService{

    @Override
    public PaginatesDto getInfoPaginates(int page, int limit, int totalData) {
        PaginatesDto paginates = new PaginatesDto();
        paginates.setLimit(limit);
        paginates.setTotalPage(setInfoTotalPage(totalData, limit));
        paginates.setPage(checkCurrentPage(page, paginates.getTotalPage()));
        paginates.setStart(findStart(paginates.getPage(), limit));
        paginates.setEnd(findEnd(paginates.getStart(), limit, totalData));
        return paginates;
    }

    private int setInfoTotalPage(int totalData, int limit) {
        int totalPage = 0;
        if(limit != 0){
            totalPage = totalData / limit;
            if(totalData % limit != 0){
                totalPage++;
            }
        }
        return totalPage;
    }

    private int checkCurrentPage(int page, int totalPage) {
        if(page < 1){
            return 1;
        }
        if(page > totalPage){
            return totalPage;
        }
        return page;
    }

    private int findStart(int page, int limit) {
        return (page - 1) * limit + 1;
    }

    private int findEnd(int start, int limit, int totalData) {
        return start + limit > totalData ? totalData : start + limit - 1;
    }
    
}
