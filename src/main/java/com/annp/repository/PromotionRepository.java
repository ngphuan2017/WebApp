/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.annp.repository;

import com.annp.pojo.Promotion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phuan
 */
public interface PromotionRepository {
    List<Promotion> getPromotions();
    List<Promotion> getPromotion(Map<String, String> params, int start, int limit);
    Promotion getPromotionById(int id);
    boolean updatePromotion(Promotion promotion);
    boolean deletePromotion(int id);
}
