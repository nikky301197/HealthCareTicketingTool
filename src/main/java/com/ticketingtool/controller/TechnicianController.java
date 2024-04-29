package com.ticketingtool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.service.TechnicianService;

@RestController
@RequestMapping("/health_care_system/api/v1/technician")
public class TechnicianController {

	@Autowired
	TechnicianService service;

	@GetMapping("/")
	public String welcome()
	{
		return "welcome technician dashboard";
	}
	
	@GetMapping("/tickets/ticket-category/{id}")
	public List<TicketDTO> viewTickets(@PathVariable Integer id) {
		List<TicketDTO> list = service.viewTickets(id);
		return list;
	}

}
