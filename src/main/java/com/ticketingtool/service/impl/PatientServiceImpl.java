package com.ticketingtool.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketingtool.dto.TicketCategoryDTO;
import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.entity.Ticket;
import com.ticketingtool.entity.TicketCategory;
import com.ticketingtool.entity.User;
import com.ticketingtool.exception.ResourceNotFoundException;
import com.ticketingtool.repository.TicketCategoryRepo;
import com.ticketingtool.repository.TicketRepo;
import com.ticketingtool.repository.UserRepo;
import com.ticketingtool.service.PatientService;
import com.ticketingtool.util.TicketStatus;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private TicketRepo ticketrepo;
	@Autowired
	private TicketCategoryRepo catrepo;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	ModelMapper modelmap;

	@Override
	public TicketDTO createTicketByPatient(TicketDTO ticketDTO) {
		TicketStatus ticketStatus = TicketStatus.OPEN;

		Integer categoryId = ticketDTO.getCategory().getCategoryId();
		String emailId = ticketDTO.getUser().getEmailId();

		TicketCategory categoryObj = catrepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));

		User userobj = userrepo.findById(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User with email id " + emailId + " not found"));

		Ticket ticket = modelmap.map(ticketDTO, Ticket.class);
		ticket.setCategory(categoryObj);
		ticket.setUser(userobj);
		ticket.setCreatedAt(new Date());
		ticket.setUpdatedAt(new Date());
		ticket.setStatus(ticketStatus.name());

		Ticket savedTicket = ticketrepo.save(ticket);
		TicketDTO ticketdtoobj = modelmap.map(savedTicket, TicketDTO.class);

		return ticketdtoobj;
	}

	@Override
	public List<TicketCategoryDTO> fetchAllTicketCategories() {
		Optional<List<TicketCategory>> ticketCatListOpt = Optional.ofNullable(catrepo.findAll());

		List<TicketCategoryDTO> ticketCatDTOList = ticketCatListOpt
				.map(ticketCatList -> ticketCatList.stream().map(cat -> modelmap.map(cat, TicketCategoryDTO.class))
						.collect(Collectors.toList()))
				.orElseThrow(() -> new ResourceNotFoundException("No Category Found!"));

		return ticketCatDTOList;

	}

	@Override
	public void deleteTicketById(Long id) {

		ticketrepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " not found."));
		ticketrepo.deleteById(id);
	}

	@Override
	public List<TicketDTO> fetchAllTickets() {
		Optional<List<Ticket>> ticketlistoptional = Optional.ofNullable(ticketrepo.findAll());

		List<TicketDTO> ticketdtolist = ticketlistoptional
				.map(ticketlist -> ticketlist.stream().map(ticket -> modelmap.map(ticket, TicketDTO.class))
						.collect(Collectors.toList()))
				.orElseThrow(() -> new ResourceNotFoundException("No Ticket Found!"));

		return ticketdtolist;
	}

}
