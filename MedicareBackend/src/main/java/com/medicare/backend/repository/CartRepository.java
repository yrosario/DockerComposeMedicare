package com.medicare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.backend.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
