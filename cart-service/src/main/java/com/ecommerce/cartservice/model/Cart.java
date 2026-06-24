package com.ecommerce.cartservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    private String userId;
    private List<CartItem> items = new ArrayList<>();
    private Integer totalItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
