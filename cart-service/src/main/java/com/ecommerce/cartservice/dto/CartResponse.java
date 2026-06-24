package com.ecommerce.cartservice.dto;

import com.ecommerce.cartservice.model.CartItem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartResponse {
    private String id;
    private String userId;
    private List<CartItem> items;
    private Integer totalItems;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
