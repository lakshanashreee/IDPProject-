package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.ApiResponse;
import com.ecommerce.paymentservice.dto.PaymentRequest;
import com.ecommerce.paymentservice.dto.PaymentResponse;
import com.ecommerce.paymentservice.dto.PaymentStatusRequest;
import com.ecommerce.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payment created", paymentService.createPayment(request)));
    }

    // Simulates a real payment: COD always succeeds, others succeed 80% randomly
    @PostMapping("/simulate")
    public ResponseEntity<ApiResponse<PaymentResponse>> simulatePayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payment simulated", paymentService.simulatePayment(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        return ResponseEntity.ok(ApiResponse.success("All payments retrieved", paymentService.getAllPayments()));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPayment(@PathVariable String paymentId) {
        return ResponseEntity.ok(ApiResponse.success("Payment retrieved", paymentService.getPaymentByPaymentId(paymentId)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getByOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order payments retrieved", paymentService.getPaymentsByOrderId(orderId)));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updateStatus(
            @PathVariable String paymentId,
            @Valid @RequestBody PaymentStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Payment status updated", paymentService.updatePaymentStatus(paymentId, request)));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(@PathVariable String paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(ApiResponse.success("Payment deleted", null));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Payment Service is running on port 8085", "UP"));
    }
}
