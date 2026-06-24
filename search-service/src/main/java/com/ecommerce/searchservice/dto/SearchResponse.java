package com.ecommerce.searchservice.dto;

import lombok.Data;

@Data
public class SearchResponse {
    private String id;
    private String productId;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean active;
}
