package com.ticketingtool.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String emailId;

	private String password;

	private String fullName;

	private String phoneNo;

	private Set<RoleDTO> roles;

}
