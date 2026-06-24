package com.ecommerce.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentStatusRequest {
    @NotBlank(message = "Status is required")
    private String status;
}
