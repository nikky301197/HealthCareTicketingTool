package com.ticketingtool.entity;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Long ticketId;

	@NotBlank(message = "Title cannot be blank")
	@Size(max = 255, message = "Title cannot exceed 255 characters")
    @Column(name = "title")
	private String title;
    
	@NotBlank(message = "Description cannot be blank")	   
	@Column(name = "description")
	private String description;

	@NotBlank(message = "Status cannot be blank")
	@Column(name = "status")
	private String status;

	@Column(name = "assined_to")
	@Value(value =  "mediplus-support@gmail.com")
	private String assinedTo;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private TicketCategory category;

	@ManyToOne
	@JoinColumn(name = "email_id")
	private User user;

}
