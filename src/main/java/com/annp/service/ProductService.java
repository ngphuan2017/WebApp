package com.annp.service;

import com.annp.pojo.Cart;
import com.annp.pojo.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getProducts(Map<String, String> params);
    Product getProductById(int id);
    boolean addOrUpdateProduct(Product p);
    boolean deleteProduct(int id);
    boolean addReceipt(Map<String, Cart> cart);
}
