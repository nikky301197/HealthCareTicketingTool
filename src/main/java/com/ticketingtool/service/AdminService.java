package com.ticketingtool.service;

import java.util.List;

import com.ticketingtool.dto.UserDTO;

public interface AdminService {

	List<UserDTO> getUsers();

	UserDTO addUser(UserDTO userdto);
	
}
