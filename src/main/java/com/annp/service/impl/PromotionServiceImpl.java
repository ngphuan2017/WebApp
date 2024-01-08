/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.service.impl;

import com.annp.pojo.Promotion;
import com.annp.pojo.Status;
import com.annp.repository.PromotionRepository;
import com.annp.service.PromotionService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author phuan
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> getPromotion(Map<String, String> params, int start, int limit) {
        return this.promotionRepository.getPromotion(params, start, limit);
    }

    @Override
    public boolean deletePromotion(int id) {
        return this.promotionRepository.deletePromotion(id);
    }

    @Override
    public Promotion getPromotionById(int id) {
        return this.promotionRepository.getPromotionById(id);
    }

    @Override
    public boolean updatePromotion(Promotion promotion) {
        return this.promotionRepository.updatePromotion(promotion);
    }

    @Override
    public List<Promotion> getPromotions() {
        return this.promotionRepository.getPromotions();
    }

    @Override
    public List<Promotion> getPromotions(Status status) {
        return this.promotionRepository.getPromotions(status);
    }

    @Override
    public boolean addPromotion(Promotion promotion) {
        if (promotion.getFile() != null && !promotion.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(promotion.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                promotion.setImg(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.promotionRepository.addPromotion(promotion);
    }

}
