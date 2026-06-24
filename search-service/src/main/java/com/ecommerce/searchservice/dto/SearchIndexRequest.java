package com.ecommerce.searchservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchIndexRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull @Min(0) private Double price;

    private Boolean active = true;
}
