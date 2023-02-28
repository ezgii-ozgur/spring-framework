package com.example.springframework.service.impl;

import com.example.springframework.enums.Language;
import com.example.springframework.exception.enums.FriendlyMessageCodes;
import com.example.springframework.exception.exceptions.ProductNotCreateException;
import com.example.springframework.repository.entity.Product;
import com.example.springframework.repository.entity.ProductRepository;
import com.example.springframework.request.ProductCreateRequest;
import com.example.springframework.request.ProductUpdatedRequest;
import com.example.springframework.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {
    private final ProductRepository productRepository;
    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}] [create product] -> request: {}", this.getClass().getSimpleName() ,productCreateRequest);
        try{
            Product product= Product.builder()
                    .productName(productCreateRequest.getProductName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            Product productResponse = productRepository.save(product);
            log.debug("[{}][create product] -> response : {} ", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        }
        catch (Exception exception){
            throw new ProductNotCreateException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION,"product request:"+productCreateRequest.toString());
        }

    }

    @Override
    public Product getProduct(Language language, Long productId) {
        return null;
    }

    @Override
    public List<Product> getProducts(Language language) {
        return null;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest) {
        return null;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        return null;
    }
}
