package com.ecommerce.paymentservice.dto;

import com.ecommerce.paymentservice.model.PaymentMode;
import com.ecommerce.paymentservice.model.PaymentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private String id;
    private String paymentId;
    private String orderId;
    private String userId;
    private Double amount;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;
    private LocalDateTime transactionTime;
}
