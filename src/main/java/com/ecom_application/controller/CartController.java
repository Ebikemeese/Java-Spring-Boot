package com.ecom_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.model.CartItem;
import com.ecom_application.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
  
    @PostMapping
    public ResponseEntity<String> addToCart(
        @RequestHeader("X-User-Id") String userId,
        @RequestBody CartItemRequest request
    ) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found");
        };
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromCart(
        @RequestHeader("X-User-Id") String userId,
        @PathVariable Long productId
    ) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping 
    public ResponseEntity<List<CartItem>> getUserCart(
        @RequestHeader("X-User-Id") String userId
    ) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }
}
