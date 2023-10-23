/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.CategorySub;
import com.annp.repository.CategorySubRepository;
import com.annp.service.CategorySubService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class CategorySubServiceImpl implements CategorySubService {

    @Autowired
    private CategorySubRepository categorySubRepository;

    @Override
    public List<CategorySub> getCategorySub() {
        return this.categorySubRepository.getCategorySub();
    }

    @Override
    public List<CategorySub> getCategorySub(Map<String, String> params, int start, int limit) {
        return this.categorySubRepository.getCategorySub(params, start, limit);
    }

    @Override
    public boolean deleteCategorySub(int id) {
        return this.categorySubRepository.deleteCategorySub(id);
    }

    @Override
    public CategorySub getCategorySubById(int id) {
        return this.categorySubRepository.getCategorySubById(id);
    }

    @Override
    public boolean updateCategorySub(CategorySub categorySub) {
        return this.categorySubRepository.updateCategorySub(categorySub);
    }

}
