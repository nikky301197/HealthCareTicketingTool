package com.ticketingtool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticketingtool.dto.TicketCategoryDTO;
import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.dto.UserDTO;
import com.ticketingtool.entity.Ticket;

@Service
public interface PatientService {
	TicketDTO createTicketByPatient(TicketDTO ticketDTO , String emailId);

	List<TicketCategoryDTO> fetchAllTicketCategories();

	void deleteTicketById(Long id);

	List<TicketDTO> fetchAllTicketByUser(String emailId);
	UserDTO registerNewPatient(UserDTO userdto);
	TicketDTO getTicketById(String emailId , Long id);

}
