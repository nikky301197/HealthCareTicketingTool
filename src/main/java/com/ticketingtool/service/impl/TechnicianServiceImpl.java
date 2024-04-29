package com.ticketingtool.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.entity.Ticket;
import com.ticketingtool.entity.TicketCategory;
import com.ticketingtool.exception.ResourceNotFoundException;
import com.ticketingtool.repository.TicketCategoryRepo;
import com.ticketingtool.repository.TicketRepo;
import com.ticketingtool.service.TechnicianService;

@Service
public class TechnicianServiceImpl implements TechnicianService {

	@Autowired
	TicketRepo ticketrepo;

	@Autowired
	TicketCategoryRepo ticketcatrepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<TicketDTO> viewTickets(Integer id) {

		TicketCategory ticketcat = ticketcatrepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket Category with id " + id + "not found"));

		List<Ticket> ticketlist = ticketrepo.findByCategory(ticketcat);
		
		List<TicketDTO> ticketlistdto = ticketlist.stream().map(ticket -> modelMapper.map(ticket, TicketDTO.class))
				.collect(Collectors.toList());
		

		return ticketlistdto;
	}

}
