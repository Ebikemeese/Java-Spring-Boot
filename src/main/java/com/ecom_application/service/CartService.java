package com.ecom_application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecom_application.dto.CartItemRequest;
import com.ecom_application.model.Product;
import com.ecom_application.model.User;
import com.ecom_application.model.CartItem;
import com.ecom_application.repository.ProductRepository;
import com.ecom_application.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.ecom_application.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional 
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());

        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(existingCartItem.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if (productOpt.isPresent() && userOpt.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }

        return false;
    }

    public List<CartItem> getUserCart(String userId) {
        return userRepository.findById(Long.valueOf(userId))
            .map(cartItemRepository::findbyUser)
            .orElseGet(List::of);
    }
    
}
