package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByOrderId(String orderId);
    List<Order> findByUserId(String userId);
    boolean existsByOrderId(String orderId);
}
