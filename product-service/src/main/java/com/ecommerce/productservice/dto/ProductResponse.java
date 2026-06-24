package com.ecommerce.productservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer quantity;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
