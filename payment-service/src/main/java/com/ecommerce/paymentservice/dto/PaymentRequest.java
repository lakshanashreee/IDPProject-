package com.ecommerce.paymentservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull @Min(1) private Double amount;

    @NotBlank(message = "Payment mode is required (UPI, CARD, NET_BANKING, COD)")
    private String paymentMode;
}
