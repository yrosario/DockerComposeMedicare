package com.medicare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
