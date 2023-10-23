/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.CategorySub;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public interface CategorySubRepository {
    
    CategorySub getCategorySubById(int id);
    List<CategorySub> getCategorySub();
    List<CategorySub> getCategorySub(Map<String, String> params, int start, int limit);
    boolean updateCategorySub(CategorySub categorySub);
    boolean deleteCategorySub(int id);
}
