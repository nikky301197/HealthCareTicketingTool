package com.ticketingtool.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
	private Long ticketId;
	private String title;
	private String description;
	private String status;
	private String assinedTo;
	private Date createdAt;
	private Date updatedAt;
	private TicketCategoryDTO category;
	private UserDTO user;
	// Add other fields as needed
}
   