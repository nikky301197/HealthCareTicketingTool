package com.ticketingtool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketingtool.dto.TicketCategoryDTO;
import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/health_care_system/api/v1/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping("/")
	public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
		// Call the service to create the ticket
		TicketDTO createdTicket = patientService.createTicketByPatient(ticketDTO);
		return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@GetMapping("/ticket-categories")
	public ResponseEntity<List<TicketCategoryDTO>> fetchAllTicketCategories() {
		List<TicketCategoryDTO> listcatdto = patientService.fetchAllTicketCategories();
		return new ResponseEntity<List<TicketCategoryDTO>>(listcatdto, HttpStatus.OK);
	}
	
	@GetMapping("/tickets")
	public ResponseEntity<List<TicketDTO>> fetchAllTicket() {
		List<TicketDTO> listticketdto = patientService.fetchAllTickets();
		return new ResponseEntity<List<TicketDTO>>(listticketdto, HttpStatus.OK);
	}

	@DeleteMapping("/tickets/{id}")
	public String deleteTicketById(@PathVariable Long id) {
		patientService.deleteTicketById(id);
		return "Ticket deleted successfully";
	}

	
}
