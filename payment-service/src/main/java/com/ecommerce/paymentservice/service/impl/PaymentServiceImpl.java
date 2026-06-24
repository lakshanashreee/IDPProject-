package com.ecommerce.paymentservice.service.impl;

import com.ecommerce.paymentservice.dto.PaymentRequest;
import com.ecommerce.paymentservice.dto.PaymentResponse;
import com.ecommerce.paymentservice.dto.PaymentStatusRequest;
import com.ecommerce.paymentservice.exception.PaymentNotFoundException;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentMode;
import com.ecommerce.paymentservice.model.PaymentStatus;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = buildPayment(request, PaymentStatus.PENDING);
        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse simulatePayment(PaymentRequest request) {
        Payment payment = buildPayment(request, PaymentStatus.PENDING);

        // Simulate: COD always succeeds; others succeed 80% of the time randomly
        if (payment.getPaymentMode() == PaymentMode.COD) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
        } else {
            boolean success = Math.random() < 0.8;
            payment.setPaymentStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        }

        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentResponse getPaymentByPaymentId(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + paymentId));
        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentResponse updatePaymentStatus(String paymentId, PaymentStatusRequest request) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + paymentId));
        try {
            payment.setPaymentStatus(PaymentStatus.valueOf(request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + request.getStatus()
                    + ". Valid: PENDING, SUCCESS, FAILED, REFUNDED");
        }
        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public void deletePayment(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + paymentId));
        paymentRepository.deleteById(payment.getId());
    }

    private Payment buildPayment(PaymentRequest request, PaymentStatus status) {
        Payment payment = new Payment();
        payment.setPaymentId("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setOrderId(request.getOrderId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        try {
            payment.setPaymentMode(PaymentMode.valueOf(request.getPaymentMode().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid payment mode: " + request.getPaymentMode()
                    + ". Valid: UPI, CARD, NET_BANKING, COD");
        }
        payment.setPaymentStatus(status);
        payment.setTransactionTime(LocalDateTime.now());
        return payment;
    }

    private PaymentResponse mapToResponse(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.setId(p.getId());
        r.setPaymentId(p.getPaymentId());
        r.setOrderId(p.getOrderId());
        r.setUserId(p.getUserId());
        r.setAmount(p.getAmount());
        r.setPaymentMode(p.getPaymentMode());
        r.setPaymentStatus(p.getPaymentStatus());
        r.setTransactionTime(p.getTransactionTime());
        return r;
    }
}
