package com.ticketingtool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketingtool.entity.Ticket;
import com.ticketingtool.entity.TicketCategory;
import com.ticketingtool.entity.User;


@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {
   
	List<Ticket> findByCategory(TicketCategory category);
	List<Ticket> findByUser(User user);
}
