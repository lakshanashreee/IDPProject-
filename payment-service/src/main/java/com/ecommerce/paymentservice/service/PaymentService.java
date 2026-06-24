package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PaymentRequest;
import com.ecommerce.paymentservice.dto.PaymentResponse;
import com.ecommerce.paymentservice.dto.PaymentStatusRequest;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse simulatePayment(PaymentRequest request);
    List<PaymentResponse> getAllPayments();
    PaymentResponse getPaymentByPaymentId(String paymentId);
    List<PaymentResponse> getPaymentsByOrderId(String orderId);
    PaymentResponse updatePaymentStatus(String paymentId, PaymentStatusRequest request);
    void deletePayment(String paymentId);
}
