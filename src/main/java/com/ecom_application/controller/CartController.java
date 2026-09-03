package com.ecom_application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
  
    @PostMapping
    public ResponseEntity<Void> addToCart(
        @RequestHeader("X-User-Id") String userId,
        @RequestBody CartItemRequest request
    ) {
        cartService.addToCart(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
