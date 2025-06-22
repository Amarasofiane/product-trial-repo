package com.product.trial.backend.service;

import java.util.List;

import com.product.trial.backend.entity.Product;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
}
