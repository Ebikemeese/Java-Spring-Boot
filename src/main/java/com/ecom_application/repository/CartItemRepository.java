package com.ecom_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom_application.model.CardItem;

@Repository
public interface CartItemRepository extends JpaRepository<CardItem, Long> {
}
