package com.ecommerce.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventory")
public class Inventory {

    @Id
    private String id;

    private String productId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer lowStockThreshold;
    private LocalDateTime lastUpdated;
}
