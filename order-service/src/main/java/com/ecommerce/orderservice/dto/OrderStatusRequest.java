package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderStatusRequest {
    @NotBlank(message = "Status is required")
    private String status;
}
