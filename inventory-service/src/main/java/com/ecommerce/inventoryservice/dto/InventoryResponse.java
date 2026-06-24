package com.ecommerce.inventoryservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryResponse {
    private String id;
    private String productId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer lowStockThreshold;
    private LocalDateTime lastUpdated;
    private boolean lowStock;
}
