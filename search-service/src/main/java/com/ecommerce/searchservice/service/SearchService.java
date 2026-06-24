package com.ecommerce.searchservice.service;

import com.ecommerce.searchservice.dto.SearchIndexRequest;
import com.ecommerce.searchservice.dto.SearchResponse;

import java.util.List;

public interface SearchService {
    SearchResponse indexProduct(SearchIndexRequest request);
    List<SearchResponse> searchByKeyword(String keyword);
    List<SearchResponse> searchByCategory(String category);
    List<SearchResponse> searchByPriceRange(Double min, Double max);
    List<SearchResponse> filterProducts(String category, Double min, Double max);
    List<String> getSuggestions(String keyword);
    void deleteIndex(String productId);
}
