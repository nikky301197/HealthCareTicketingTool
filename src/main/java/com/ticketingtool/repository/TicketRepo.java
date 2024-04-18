package com.ticketingtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketingtool.entity.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
    // You can add custom query methods here if needed
}
