package com.annp.repository;

import com.annp.pojo.Cart;
import com.annp.pojo.Product;
import com.annp.pojo.ProductImages;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    List<Product> getProducts(Map<String, String> params, int start, int limit);
    Product getProductById(int id);
    ProductImages getImagesByProductId(Product p);
    boolean addOrUpdateProduct(Product p);
    boolean updateProduct(Product p);
    boolean deleteProduct(int id);
    boolean addOrUpdateProductImages(ProductImages img);
    boolean addReceipt(Map<String, Cart> cart, Integer amount, Integer discount);
    boolean addReceiptPaid(Map<String, Cart> cart, Integer amount, Integer discount);
}
