package com.ecommerce.cartservice.dto;

import lombok.Data;

@Data
public class CartSummaryResponse {
    private String userId;
    private Integer totalItems;
    private Double totalPrice;
    private Integer uniqueProducts;
}
