package com.ticketingtool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ticketingtool.dto.TicketCategoryDTO;
import com.ticketingtool.dto.TicketDTO;

@Service
public interface PatientService {
	TicketDTO createTicketByPatient(TicketDTO ticketDTO);

	List<TicketCategoryDTO> fecthAllTicketCategories();
    
}
