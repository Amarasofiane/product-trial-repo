package com.product.trial.backend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.trial.backend.entity.Product;
import com.product.trial.backend.exception.ResourceNotFoundException;
import com.product.trial.backend.repository.ProductRepository;
import com.product.trial.backend.service.ProductService;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return saveProductWithTimestamps(product, true);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = findProductById(id);
        updateProductDetails(existingProduct, product);
        return saveProductWithTimestamps(existingProduct, false);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(Long id) {
        return findProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit pas trouvé " + id));
    }

    private void updateProductDetails(Product existingProduct, Product newProduct) {
        existingProduct.setName(newProduct.getName());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setCategory(newProduct.getCategory());
        existingProduct.setDescription(newProduct.getDescription());
    }

    private Product saveProductWithTimestamps(Product product, boolean isNew) {
        long currentTimestamp = System.currentTimeMillis();
        if (isNew) {
            product.setCreatedAt(currentTimestamp);
        }
        product.setUpdatedAt(currentTimestamp);
        return productRepository.save(product);
    }
}
