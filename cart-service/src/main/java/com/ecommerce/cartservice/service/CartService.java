package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.dto.CartItemRequest;
import com.ecommerce.cartservice.dto.CartResponse;
import com.ecommerce.cartservice.dto.CartSummaryResponse;

public interface CartService {
    CartResponse addItemToCart(String userId, CartItemRequest request);
    CartResponse getCart(String userId);
    CartSummaryResponse getCartSummary(String userId);
    CartResponse updateCartItem(String userId, String productId, CartItemRequest request);
    CartResponse removeItemFromCart(String userId, String productId);
    void clearCart(String userId);
}
