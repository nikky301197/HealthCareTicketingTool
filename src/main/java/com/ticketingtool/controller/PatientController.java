package com.ticketingtool.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketingtool.dto.TicketCategoryDTO;
import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.dto.UserDTO;
import com.ticketingtool.service.PatientService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/health_care_system/api/v1/patient")
@Validated
public class PatientController {

	@Autowired
	private PatientService patientService;
	private Logger logger = LoggerFactory.getLogger(PatientController.class);

	@PostMapping("/{emailId}")
	public ResponseEntity<TicketDTO> createTicket(
			@PathVariable @NotBlank(message = "Email cannot be blank") @Email(message = "Email should be valid") String emailId,
			@Valid @RequestBody TicketDTO ticketDTO) {
		// Call the service to create the ticket
		logger.info("Patient Controller executing ..");
		TicketDTO createdTicket = patientService.createTicketByPatient(ticketDTO, emailId);
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

	@GetMapping("/tickets/users/{emailId}")
	public ResponseEntity<List<TicketDTO>> fetchAllTicketByUser(@PathVariable String emailId) {
		List<TicketDTO> listticketdto = patientService.fetchAllTicketByUser(emailId);
		return new ResponseEntity<List<TicketDTO>>(listticketdto, HttpStatus.OK);
	}

	@DeleteMapping("/ticket/{id}")
	public String deleteTicketById(@PathVariable Long id) {
		patientService.deleteTicketById(id);
		return "Ticket deleted successfully";
	}

	@PostMapping("/")
	public ResponseEntity<UserDTO> registerNewPatient(@Valid @RequestBody UserDTO user) {
		// logic to add user
		logger.info("register new patient controller executing..");
		UserDTO users = patientService.registerNewPatient(user);
		return new ResponseEntity<UserDTO>(users, HttpStatus.OK);
	}

	@GetMapping("/{emailId}/ticket/{ticketId}")
	public ResponseEntity<TicketDTO> searchTicketById(@PathVariable String emailId ,  @PathVariable Long ticketId) {
		TicketDTO foundTicket = patientService.getTicketById(emailId , ticketId);
		return new ResponseEntity<TicketDTO>(foundTicket, HttpStatus.OK);
	}

}
