package com.ecommerce.searchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "search_products")
public class SearchProduct {

    @Id
    private String id;

    @Indexed(unique = true)
    private String productId;

    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean active;
}
