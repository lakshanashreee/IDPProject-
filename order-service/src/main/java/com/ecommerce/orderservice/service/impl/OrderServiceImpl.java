package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.OrderItemRequest;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.dto.OrderStatusRequest;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.model.OrderStatus;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        Order order = new Order();
        // Generate a unique readable order ID
        order.setOrderId("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUserId(request.getUserId());

        List<OrderItem> items = request.getItems().stream().map(req -> {
            OrderItem item = new OrderItem();
            item.setProductId(req.getProductId());
            item.setProductName(req.getProductName());
            item.setPrice(req.getPrice());
            item.setQuantity(req.getQuantity());
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        // Calculate total amount from all items
        double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        order.setTotalAmount(total);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPaymentStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrderStatus(String orderId, OrderStatusRequest request) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        try {
            order.setOrderStatus(OrderStatus.valueOf(request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + request.getStatus()
                    + ". Valid values: PLACED, CONFIRMED, PACKED, SHIPPED, DELIVERED, CANCELLED");
        }
        order.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        orderRepository.deleteById(order.getId());
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setOrderId(order.getOrderId());
        r.setUserId(order.getUserId());
        r.setItems(order.getItems());
        r.setTotalAmount(order.getTotalAmount());
        r.setOrderStatus(order.getOrderStatus());
        r.setPaymentStatus(order.getPaymentStatus());
        r.setCreatedAt(order.getCreatedAt());
        r.setUpdatedAt(order.getUpdatedAt());
        return r;
    }
}
