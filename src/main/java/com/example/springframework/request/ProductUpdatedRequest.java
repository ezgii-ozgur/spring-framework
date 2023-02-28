package com.example.springframework.request;

import lombok.Data;

@Data
public class ProductUpdatedRequest {
    private long productId ;
    private String productName ;
    private Integer quantity ;
    private double price ;
}
