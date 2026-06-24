package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.ApiResponse;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.dto.OrderStatusRequest;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order placed successfully", orderService.placeOrder(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success("All orders retrieved", orderService.getAllOrders()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order retrieved", orderService.getOrderByOrderId(orderId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUser(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success("User orders retrieved", orderService.getOrdersByUserId(userId)));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateStatus(
            @PathVariable String orderId,
            @Valid @RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Order status updated", orderService.updateOrderStatus(orderId, request)));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order deleted", null));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Order Service is running on port 8084", "UP"));
    }
}
