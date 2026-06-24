package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByProductId(String productId);
    boolean existsByProductId(String productId);
    void deleteByProductId(String productId);

    // Find items where available quantity is at or below the threshold
    List<Inventory> findByAvailableQuantityLessThanEqual(Integer threshold);
}
