package com.ecommerce.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    private String paymentId;       // Human-readable PAY-XXXXX
    private String orderId;
    private String userId;
    private Double amount;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;
    private LocalDateTime transactionTime;
}
