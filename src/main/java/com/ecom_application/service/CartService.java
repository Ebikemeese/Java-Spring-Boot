package com.ecom_application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.model.Product;
import com.ecom_application.repository.ProductRepository;
import com.ecom_application.repository.UserRepository;
import com.ecom_application.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> product = productRepository.findById(request.getProductId());

        if (product.isEmpty()) {
            return true;
        }
        return false;
    }
    
}
