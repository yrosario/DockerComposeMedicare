package com.medicare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
