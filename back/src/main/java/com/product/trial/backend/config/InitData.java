package com.product.trial.backend.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.trial.backend.entity.Product;
import com.product.trial.backend.repository.ProductRepository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class InitData {

    private final ProductRepository productRepository;


    public InitData(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() throws IOException { // stockage des données fichier product.json en BDD H2
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("products.json");

        List<Product> products = Arrays.asList(objectMapper.readValue(resource.getInputStream(), Product[].class));
        productRepository.saveAll(products);
    }
}
