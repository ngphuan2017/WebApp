package com.annp.service.impl;

import com.annp.pojo.Cart;
import com.annp.pojo.Product;
import com.annp.repository.ProductRepository;
import com.annp.service.ProductService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts(Map<String, String> params) {
        return this.productRepository.getProducts(params);
    }

    @Override
    public Product getProductById(int id) {
        return this.productRepository.getProductById(id);
    }

    @Override
    public boolean addOrUpdateProduct(Product p) {
        try {
            if (p.getFile() != null && !p.getFile().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(p.getFile().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    p.setImage(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return this.productRepository.addOrUpdateProduct(p);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int id) {
        return this.productRepository.deleteProduct(id);
    }

    @Override
    public boolean addReceipt(Map<String, Cart> cart) {
        if (cart != null) {
            return this.productRepository.addReceipt(cart);
        }
        return false;
    }
}
