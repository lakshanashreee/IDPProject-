package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.dto.OrderStatusRequest;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderByOrderId(String orderId);
    List<OrderResponse> getOrdersByUserId(String userId);
    OrderResponse updateOrderStatus(String orderId, OrderStatusRequest request);
    void deleteOrder(String orderId);
}
