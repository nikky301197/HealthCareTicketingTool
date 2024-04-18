package com.ticketingtool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/")
	public String welcome() {
		return "welcome";
	}
	
//	List<TicketCategoryDTO> fecthAllTicketCategories()
//	{
//	patientService.fecthAllTicketCategories();	
//	}

}
