package com.ticketingtool.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email should be valid")
	@Column(name = "email_id")
	private String emailId;

	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Column(name = "password")
	private String password;

	@NotBlank(message = "Full name cannot be blank")
	@Column(name = "fullName")
	private String fullName;

	@NotBlank(message = "Phone number cannot be blank")
	@Size(min = 10, max = 10, message = "Phone number must be 10 digits long")
	@Column(name = "phoneNo")
	private String phoneNo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "emailId", referencedColumnName = "email_id"), inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "role_id"))
	private Set<Role> roles;
}
