package com.ticketingtool.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ticketingtool.dto.TicketCategoryDTO;
import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.dto.UserDTO;
import com.ticketingtool.entity.Role;
import com.ticketingtool.entity.Ticket;
import com.ticketingtool.entity.TicketCategory;
import com.ticketingtool.entity.User;
import com.ticketingtool.exception.ResourceAlreadyExistException;
import com.ticketingtool.exception.ResourceNotFoundException;
import com.ticketingtool.repository.RoleRepo;
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
	private RoleRepo rolerepo;
	@Autowired
	ModelMapper modelmap;
	@Autowired
	PasswordEncoder passwordencoder;
	private static final String ROLE_NAME = "ROLE_PATIENT";
	private Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Override
	public TicketDTO createTicketByPatient(TicketDTO ticketDTO, String emailId) {
		logger.info("Patien Service executing ..");
		TicketStatus ticketStatus = TicketStatus.OPEN;

		Integer categoryId = ticketDTO.getCategory().getCategoryId();
//		String emailId = ticketDTO.getUser().getEmailId();

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
		logger.info("Patien Service executing -  ticket generate " + ticket);
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
	public List<TicketDTO> fetchAllTicketByUser(String emailId) {
		User user = userrepo.findById(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User with email id " + emailId + " not found"));

		Optional<List<Ticket>> ticketlistoptional = Optional.ofNullable(ticketrepo.findByUser(user));

		List<TicketDTO> ticketdtolist = ticketlistoptional
				.map(ticketlist -> ticketlist.stream().map(ticket -> modelmap.map(ticket, TicketDTO.class))
						.collect(Collectors.toList()))
				.orElseThrow(() -> new ResourceNotFoundException("No Ticket Found!"));

		return ticketdtolist;
	}

	@Override
	public UserDTO registerNewPatient(UserDTO userdto) {

		User user = modelmap.map(userdto, User.class);

		Boolean checkPatientExistence = userrepo.existsById(userdto.getEmailId());
		if (checkPatientExistence) {
			throw new ResourceAlreadyExistException("Patient with email id " + user.getEmailId() + " already exist !");
		}
		user.setPassword(passwordencoder.encode(userdto.getPassword()));

		Role role = rolerepo.findByRoleName(ROLE_NAME)
				.orElseThrow(() -> new ResourceNotFoundException("Role with " + ROLE_NAME + " not found!"));
		Set<Role> rolelist = new HashSet<>();
		rolelist.add(role);
		user.setRoles(rolelist);
		User saveduser = userrepo.save(user);
		UserDTO dto = modelmap.map(saveduser, UserDTO.class);
		return dto;
	}

	@Override
	public TicketDTO getTicketById(String emailId, Long id) {
		User user = userrepo.findById(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User with email id " + emailId + " not found"));

		Optional<List<Ticket>> ticketlistoptional = Optional.ofNullable(ticketrepo.findByUser(user));
		List<Ticket> ticketlist = ticketlistoptional.get();
		if (ticketlist.isEmpty()) {
			throw new ResourceNotFoundException("Ticket Not Found");
		} else {
			Optional<Ticket> foundTicket = ticketlist.stream().filter(ticket -> ticket.getTicketId() == id).findFirst();
			if (foundTicket.isEmpty()) {
				throw new ResourceNotFoundException("Ticket not found with id : " + id);
			}
			return modelmap.map(foundTicket.get(), TicketDTO.class);
		}

	}

}
