package com.medicare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.backend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
