package com.medicare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.backend.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
