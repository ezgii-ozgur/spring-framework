package com.example.springframework.controller;

import com.example.springframework.enums.Language;
import com.example.springframework.exception.enums.FriendlyMessageCodes;
import com.example.springframework.exception.utils.FriendlyMessageUtils;
import com.example.springframework.repository.entity.Product;
import com.example.springframework.request.ProductCreateRequest;
import com.example.springframework.response.FriendlyMessage;
import com.example.springframework.response.InternalApiResponse;
import com.example.springframework.response.ProductResponse;
import com.example.springframework.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/product")
@RequiredArgsConstructor
class ProductController {
    private final IProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language")Language language,
                                                              @RequestBody ProductCreateRequest productCreateRequest){
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        Product product = productRepositoryService.createProduct(language, productCreateRequest);
        ProductResponse productResponse= convertProductResponse(product);
        log.debug("[{}][createProduct] -> request : {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_CREATED_EXCEPTION))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder().
                productId(product.getProductId()).
                productName(product.getProductName()).
                quantity(product.getQuantity()).
                price(product.getPrice()).
                productCreatedDate(product.getProductCreatedDate().getTime()).
                productUpdatedDate(product.getProductUpdatedDate().getTime())
                .build();
    }
}
