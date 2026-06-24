package com.ecommerce.searchservice.repository;

import com.ecommerce.searchservice.model.SearchProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchProductRepository extends MongoRepository<SearchProduct, String> {
    Optional<SearchProduct> findByProductId(String productId);
    boolean existsByProductId(String productId);
    void deleteByProductId(String productId);
    List<SearchProduct> findByCategory(String category);
    List<SearchProduct> findByPriceBetween(Double min, Double max);
    List<SearchProduct> findByCategoryAndPriceBetween(String category, Double min, Double max);

    // MongoDB text search via regex for keyword matching
    List<SearchProduct> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
