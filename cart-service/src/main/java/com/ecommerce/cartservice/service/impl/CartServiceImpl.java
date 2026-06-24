package com.ecommerce.cartservice.service.impl;

import com.ecommerce.cartservice.dto.CartItemRequest;
import com.ecommerce.cartservice.dto.CartResponse;
import com.ecommerce.cartservice.dto.CartSummaryResponse;
import com.ecommerce.cartservice.exception.CartNotFoundException;
import com.ecommerce.cartservice.model.Cart;
import com.ecommerce.cartservice.model.CartItem;
import com.ecommerce.cartservice.repository.CartRepository;
import com.ecommerce.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartResponse addItemToCart(String userId, CartItemRequest request) {
        // Find existing cart or create a new one for the user
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setItems(new ArrayList<>());
            newCart.setCreatedAt(LocalDateTime.now());
            return newCart;
        });

        List<CartItem> items = cart.getItems();
        Optional<CartItem> existingItem = items.stream()
                .filter(i -> i.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // If item exists, just increase the quantity
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setProductName(request.getProductName());
            newItem.setPrice(request.getPrice());
            newItem.setQuantity(request.getQuantity());
            items.add(newItem);
        }

        cart.setTotalItems(items.stream().mapToInt(CartItem::getQuantity).sum());
        cart.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse getCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));
        return mapToResponse(cart);
    }

    @Override
    public CartSummaryResponse getCartSummary(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));

        CartSummaryResponse summary = new CartSummaryResponse();
        summary.setUserId(userId);
        summary.setTotalItems(cart.getItems().stream().mapToInt(CartItem::getQuantity).sum());
        summary.setTotalPrice(cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());
        summary.setUniqueProducts(cart.getItems().size());
        return summary;
    }

    @Override
    public CartResponse updateCartItem(String userId, String productId, CartItemRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new CartNotFoundException("Item not found in cart: " + productId));

        item.setQuantity(request.getQuantity());
        item.setPrice(request.getPrice());
        cart.setTotalItems(cart.getItems().stream().mapToInt(CartItem::getQuantity).sum());
        cart.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse removeItemFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));

        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        cart.setTotalItems(cart.getItems().stream().mapToInt(CartItem::getQuantity).sum());
        cart.setUpdatedAt(LocalDateTime.now());
        return mapToResponse(cartRepository.save(cart));
    }

    @Override
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for userId: " + userId));
        cart.setItems(new ArrayList<>());
        cart.setTotalItems(0);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private CartResponse mapToResponse(Cart cart) {
        CartResponse r = new CartResponse();
        r.setId(cart.getId());
        r.setUserId(cart.getUserId());
        r.setItems(cart.getItems());
        r.setTotalItems(cart.getTotalItems());
        r.setTotalPrice(cart.getItems().stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());
        r.setCreatedAt(cart.getCreatedAt());
        r.setUpdatedAt(cart.getUpdatedAt());
        return r;
    }
}
