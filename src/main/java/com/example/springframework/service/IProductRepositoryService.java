package com.example.springframework.service;

import com.example.springframework.enums.Language;
import com.example.springframework.repository.entity.Product;
import com.example.springframework.request.ProductCreateRequest;
import com.example.springframework.request.ProductUpdatedRequest;

import java.util.List;

public interface IProductRepositoryService {

    Product createProduct(Language language, ProductCreateRequest productCreateRequest);

    Product getProduct(Language language , Long productId);

    List<Product> getProducts(Language language);

    Product updateProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest);
    Product deleteProduct(Language language, Long productId);
}
