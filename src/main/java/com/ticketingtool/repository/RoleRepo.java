package com.ticketingtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketingtool.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    // You can add custom query methods here if needed
}
