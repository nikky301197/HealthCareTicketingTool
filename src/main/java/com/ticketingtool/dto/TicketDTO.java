package com.ticketingtool.dto;

import java.util.Date;

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
	private Date createdAt;
	private Date updatedAt;
	private TicketCategoryDTO category;
	private UserDTO user;
	// Add other fields as needed
}
   