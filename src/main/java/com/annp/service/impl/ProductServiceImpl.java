package com.annp.service.impl;

import com.annp.pojo.Cart;
import com.annp.pojo.Product;
import com.annp.pojo.ProductImages;
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
    public List<Product> getProducts(Map<String, String> params, int start, int limit) {
        return this.productRepository.getProducts(params, start, limit);
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
            Integer amount = 0;
            for (Cart c: cart.values()) {
                amount += c.getQuantity()*c.getPrice();
            }
            return this.productRepository.addReceipt(cart, amount);
        }
        return false;
    }

    @Override
    public boolean addReceiptPaid(Map<String, Cart> cart) {
        if (cart != null) {
            Integer amount = 0;
            for (Cart c: cart.values()) {
                amount += c.getQuantity()*c.getPrice();
            }
            return this.productRepository.addReceiptPaid(cart, amount);
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product p) {
        return this.productRepository.updateProduct(p);
    }

    @Override
    public ProductImages getImagesByProductId(Product p) {
        return this.productRepository.getImagesByProductId(p);
    }

    @Override
    public boolean addOrUpdateProductImages(ProductImages img) {
        try {
            if (img.getFile1() != null && !img.getFile1().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(img.getFile1().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    img.setImg1(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (img.getFile2() != null && !img.getFile2().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(img.getFile2().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    img.setImg2(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (img.getFile3() != null && !img.getFile3().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(img.getFile3().getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    img.setImg3(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return this.productRepository.addOrUpdateProductImages(img);
        } catch (Exception e) {
            return false;
        }
    }

}
