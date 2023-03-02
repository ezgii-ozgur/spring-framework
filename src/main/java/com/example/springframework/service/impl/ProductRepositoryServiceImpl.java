package com.example.springframework.service.impl;

import com.example.springframework.enums.Language;
import com.example.springframework.exception.enums.FriendlyMessageCodes;
import com.example.springframework.exception.exceptions.ProductNotCreateException;
import com.example.springframework.exception.exceptions.ProductAlreadyDeletedException;
import com.example.springframework.exception.exceptions.ProductNotFoundException;
import com.example.springframework.repository.entity.Product;
import com.example.springframework.repository.entity.ProductRepository;
import com.example.springframework.request.ProductCreateRequest;
import com.example.springframework.request.ProductUpdatedRequest;
import com.example.springframework.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {
    private final ProductRepository productRepository;
    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}] [createProduct] -> request: {}", this.getClass().getSimpleName() ,productCreateRequest);
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
        log.debug("[{}] [getProduct] -> request: {}", this.getClass().getSimpleName() ,productId);

        Product product = productRepository.getByProductIdAndDeletedFalse(productId);
        if(Objects.isNull(product)){
            throw new ProductNotFoundException(language,FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "Product not found for product id :"+productId);
        }
        log.debug("[{}] [get product] -> response: {}", this.getClass().getSimpleName() ,product);
        return product;


    }

    @Override
    public List<Product> getProducts(Language language) {
        log.debug("[{}] [getProductList] request ", this.getClass().getSimpleName());
        List<Product> products = productRepository.getAllByDeletedFalse();
        if(Objects.isNull(products)){
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION,"Products not found.");
        }
        log.debug("[{}] [get product] -> response: {}", this.getClass().getSimpleName() ,products);
        return products;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdatedRequest productUpdatedRequest) {
        log.debug("[{}] [updateProduct] -> request: {} -> productId : {}", this.getClass().getSimpleName() ,productUpdatedRequest, productId);
        Product product = getProduct(language, productId);
        product.setProductName(productUpdatedRequest.getProductName());
        product.setQuantity(productUpdatedRequest.getQuantity());
        product.setPrice(productUpdatedRequest.getPrice());
        product.setProductCreatedDate(product.getProductCreatedDate());
        product.setProductUpdatedDate(new Date());
        Product productResponse = productRepository.save(product);
        log.debug("[{}] [updateProduct] -> response: {} ", this.getClass().getSimpleName() ,productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        log.debug("[{}] [deleteProduct] -> request productId : {}", this.getClass().getSimpleName() , productId);
        Product product ;
        try{
            product = getProduct(language, productId);
            product.setDeleted(true);
            product.setProductUpdatedDate(new Date());
            Product productResponse = productRepository.save(product);
            log.debug("[{}][deleteProduct] -> response: {} ", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        }
        catch (ProductAlreadyDeletedException productAlreadyDeletedException){
            throw new ProductAlreadyDeletedException(language , FriendlyMessageCodes.PRODUCT_ALREADY_DELETED_EXCEPTION,"Product already deleted product id: "+productId);
        }
    }
}
