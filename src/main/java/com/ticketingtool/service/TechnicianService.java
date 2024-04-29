package com.ticketingtool.service;

import java.util.List;

import com.ticketingtool.dto.TicketDTO;

public interface TechnicianService {

	List<TicketDTO> viewTickets(Integer id);

}
