package com.ecommerce.searchservice.service.impl;

import com.ecommerce.searchservice.dto.SearchIndexRequest;
import com.ecommerce.searchservice.dto.SearchResponse;
import com.ecommerce.searchservice.exception.SearchProductNotFoundException;
import com.ecommerce.searchservice.model.SearchProduct;
import com.ecommerce.searchservice.repository.SearchProductRepository;
import com.ecommerce.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchProductRepository searchProductRepository;

    @Override
    public SearchResponse indexProduct(SearchIndexRequest request) {
        // If product already indexed, update it
        SearchProduct product = searchProductRepository.findByProductId(request.getProductId())
                .orElse(new SearchProduct());

        product.setProductId(request.getProductId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setActive(request.getActive() != null ? request.getActive() : true);

        return mapToResponse(searchProductRepository.save(product));
    }

    @Override
    public List<SearchResponse> searchByKeyword(String keyword) {
        // Search in both name and description fields (case-insensitive)
        return searchProductRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchResponse> searchByCategory(String category) {
        return searchProductRepository.findByCategory(category)
                .stream()
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchResponse> searchByPriceRange(Double min, Double max) {
        return searchProductRepository.findByPriceBetween(min, max)
                .stream()
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SearchResponse> filterProducts(String category, Double min, Double max) {
        return searchProductRepository.findByCategoryAndPriceBetween(category, min, max)
                .stream()
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSuggestions(String keyword) {
        // Return product names that start with or contain the keyword
        return searchProductRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .map(SearchProduct::getName)
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteIndex(String productId) {
        if (!searchProductRepository.existsByProductId(productId)) {
            throw new SearchProductNotFoundException("Indexed product not found: " + productId);
        }
        searchProductRepository.deleteByProductId(productId);
    }

    private SearchResponse mapToResponse(SearchProduct p) {
        SearchResponse r = new SearchResponse();
        r.setId(p.getId());
        r.setProductId(p.getProductId());
        r.setName(p.getName());
        r.setDescription(p.getDescription());
        r.setCategory(p.getCategory());
        r.setPrice(p.getPrice());
        r.setActive(p.getActive());
        return r;
    }
}
