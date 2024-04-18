package com.ticketingtool.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketingtool.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    // You can add custom query methods here if needed
}
