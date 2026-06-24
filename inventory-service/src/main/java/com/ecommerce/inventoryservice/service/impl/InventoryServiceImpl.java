package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.dto.StockUpdateRequest;
import com.ecommerce.inventoryservice.exception.InsufficientStockException;
import com.ecommerce.inventoryservice.exception.InventoryNotFoundException;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new RuntimeException("Inventory already exists for productId: " + request.getProductId());
        }
        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setAvailableQuantity(request.getAvailableQuantity());
        inventory.setReservedQuantity(request.getReservedQuantity());
        inventory.setLowStockThreshold(request.getLowStockThreshold());
        inventory.setLastUpdated(LocalDateTime.now());

        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponse getByProductId(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for productId: " + productId));
        return mapToResponse(inventory);
    }

    @Override
    public InventoryResponse addStock(String productId, StockUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for productId: " + productId));

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + request.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());
        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryResponse reduceStock(String productId, StockUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for productId: " + productId));

        // Check if enough stock is available before reducing
        if (inventory.getAvailableQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock. Available: "
                    + inventory.getAvailableQuantity() + ", Requested: " + request.getQuantity());
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - request.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());
        return mapToResponse(inventoryRepository.save(inventory));
    }

    @Override
    public List<InventoryResponse> getLowStockItems() {
        // A product is low stock when available quantity <= its own threshold
        return inventoryRepository.findAll().stream()
                .filter(inv -> inv.getAvailableQuantity() <= inv.getLowStockThreshold())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(String productId) {
        if (!inventoryRepository.existsByProductId(productId)) {
            throw new InventoryNotFoundException("Inventory not found for productId: " + productId);
        }
        inventoryRepository.deleteByProductId(productId);
    }

    private InventoryResponse mapToResponse(Inventory inv) {
        InventoryResponse r = new InventoryResponse();
        r.setId(inv.getId());
        r.setProductId(inv.getProductId());
        r.setAvailableQuantity(inv.getAvailableQuantity());
        r.setReservedQuantity(inv.getReservedQuantity());
        r.setLowStockThreshold(inv.getLowStockThreshold());
        r.setLastUpdated(inv.getLastUpdated());
        r.setLowStock(inv.getAvailableQuantity() <= inv.getLowStockThreshold());
        return r;
    }
}
