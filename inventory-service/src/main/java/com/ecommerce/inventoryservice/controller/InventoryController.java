package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.ApiResponse;
import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.dto.StockUpdateRequest;
import com.ecommerce.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> createInventory(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Inventory created", inventoryService.createInventory(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventory() {
        return ResponseEntity.ok(ApiResponse.success("All inventory retrieved", inventoryService.getAllInventory()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getByProductId(@PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success("Inventory retrieved", inventoryService.getByProductId(productId)));
    }

    @PutMapping("/{productId}/add-stock")
    public ResponseEntity<ApiResponse<InventoryResponse>> addStock(
            @PathVariable String productId,
            @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stock added successfully", inventoryService.addStock(productId, request)));
    }

    @PutMapping("/{productId}/reduce-stock")
    public ResponseEntity<ApiResponse<InventoryResponse>> reduceStock(
            @PathVariable String productId,
            @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stock reduced successfully", inventoryService.reduceStock(productId, request)));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getLowStockItems() {
        return ResponseEntity.ok(ApiResponse.success("Low stock items retrieved", inventoryService.getLowStockItems()));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(@PathVariable String productId) {
        inventoryService.deleteInventory(productId);
        return ResponseEntity.ok(ApiResponse.success("Inventory deleted", null));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Inventory Service is running on port 8082", "UP"));
    }
}
