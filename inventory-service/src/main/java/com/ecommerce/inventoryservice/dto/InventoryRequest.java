package com.ecommerce.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotNull(message = "Available quantity is required")
    @Min(value = 0, message = "Available quantity must be non-negative")
    private Integer availableQuantity;

    @Min(value = 0, message = "Reserved quantity must be non-negative")
    private Integer reservedQuantity = 0;

    @Min(value = 0, message = "Low stock threshold must be non-negative")
    private Integer lowStockThreshold = 10;
}
