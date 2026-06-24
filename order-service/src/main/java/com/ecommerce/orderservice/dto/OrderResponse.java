package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.model.OrderStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
