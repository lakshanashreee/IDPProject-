package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.dto.StockUpdateRequest;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest request);
    List<InventoryResponse> getAllInventory();
    InventoryResponse getByProductId(String productId);
    InventoryResponse addStock(String productId, StockUpdateRequest request);
    InventoryResponse reduceStock(String productId, StockUpdateRequest request);
    List<InventoryResponse> getLowStockItems();
    void deleteInventory(String productId);
}
