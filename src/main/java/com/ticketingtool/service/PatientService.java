package com.ticketingtool.service;

import org.springframework.stereotype.Service;

import com.ticketingtool.dto.TicketDTO;
import com.ticketingtool.entity.Ticket;

@Service
public interface PatientService {
	TicketDTO createTicketByPatient(TicketDTO ticketDTO);
    
}
