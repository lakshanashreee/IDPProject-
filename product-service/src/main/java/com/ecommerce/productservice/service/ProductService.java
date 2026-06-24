package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(String id);
    List<ProductResponse> getProductsByCategory(String category);
    ProductResponse updateProduct(String id, ProductRequest request);
    void deleteProduct(String id);
}
