package com.ecommerce.searchservice.controller;

import com.ecommerce.searchservice.dto.ApiResponse;
import com.ecommerce.searchservice.dto.SearchIndexRequest;
import com.ecommerce.searchservice.dto.SearchResponse;
import com.ecommerce.searchservice.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/index")
    public ResponseEntity<ApiResponse<SearchResponse>> indexProduct(@Valid @RequestBody SearchIndexRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product indexed successfully", searchService.indexProduct(request)));
    }

    // Search by keyword in name or description: GET /api/search?keyword=laptop
    @GetMapping
    public ResponseEntity<ApiResponse<List<SearchResponse>>> searchByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Search results", searchService.searchByKeyword(keyword)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<SearchResponse>>> searchByCategory(@PathVariable String category) {
        return ResponseEntity.ok(ApiResponse.success("Category results", searchService.searchByCategory(category)));
    }

    // Search by price range: GET /api/search/price-range?min=100&max=500
    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<List<SearchResponse>>> searchByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(ApiResponse.success("Price range results", searchService.searchByPriceRange(min, max)));
    }

    // Combined filter: GET /api/search/filter?category=Electronics&min=100&max=1000
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<SearchResponse>>> filterProducts(
            @RequestParam String category,
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(ApiResponse.success("Filtered results", searchService.filterProducts(category, min, max)));
    }

    // Autocomplete suggestions: GET /api/search/suggestions?keyword=lapt
    @GetMapping("/suggestions")
    public ResponseEntity<ApiResponse<List<String>>> getSuggestions(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Suggestions", searchService.getSuggestions(keyword)));
    }

    @DeleteMapping("/index/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteIndex(@PathVariable String productId) {
        searchService.deleteIndex(productId);
        return ResponseEntity.ok(ApiResponse.success("Product removed from index", null));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Search Service is running on port 8086", "UP"));
    }
}
