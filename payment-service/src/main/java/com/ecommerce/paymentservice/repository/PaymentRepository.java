package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByPaymentId(String paymentId);
    List<Payment> findByOrderId(String orderId);
    boolean existsByPaymentId(String paymentId);
}
