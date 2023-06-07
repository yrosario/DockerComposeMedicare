package com.medicare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.backend.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
