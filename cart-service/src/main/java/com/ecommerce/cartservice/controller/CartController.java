package com.ecommerce.cartservice.controller;

import com.ecommerce.cartservice.dto.ApiResponse;
import com.ecommerce.cartservice.dto.CartItemRequest;
import com.ecommerce.cartservice.dto.CartResponse;
import com.ecommerce.cartservice.dto.CartSummaryResponse;
import com.ecommerce.cartservice.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/items")
    public ResponseEntity<ApiResponse<CartResponse>> addItem(
            @PathVariable String userId,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Item added to cart", cartService.addItemToCart(userId, request)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success("Cart retrieved", cartService.getCart(userId)));
    }

    @GetMapping("/{userId}/summary")
    public ResponseEntity<ApiResponse<CartSummaryResponse>> getCartSummary(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success("Cart summary retrieved", cartService.getCartSummary(userId)));
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateItem(
            @PathVariable String userId,
            @PathVariable String productId,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cart item updated", cartService.updateCartItem(userId, productId, request)));
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<ApiResponse<CartResponse>> removeItem(
            @PathVariable String userId,
            @PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart", cartService.removeItemFromCart(userId, productId)));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success("Cart cleared", null));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Cart Service is running on port 8083", "UP"));
    }
}
