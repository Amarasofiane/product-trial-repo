package com.product.trial.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.product.trial.backend.entity.Product;
import com.product.trial.backend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") 
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Vérifiez que le rôle est autorisé
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @PatchMapping ("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Vérifiez que le rôle est autorisé
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Vérifiez que le rôle est autorisé
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Vérifiez que le rôle est autorisé
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Vérifiez que le rôle est autorisé
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
