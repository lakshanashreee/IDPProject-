package com.ecommerce.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String orderId;    // Human-readable order ID e.g. ORD-1234
    private String userId;
    private List<OrderItem> items;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private String paymentStatus;  // Tracks payment state: PENDING, PAID, FAILED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
