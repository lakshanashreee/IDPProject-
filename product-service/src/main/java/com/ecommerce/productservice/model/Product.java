package com.ecommerce.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
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
