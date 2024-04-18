package com.ticketingtool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketingtool.dto.UserDTO;
import com.ticketingtool.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/health_care_system/api/v1/admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	@GetMapping("/dashboard")
	public String adminDashboard() {
		// Logic to fetch and return admin dashboard data
		return "Admin Dashboard";
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getUsers() {
		// Logic to fetch and return all users
		
		List<UserDTO> users = adminService.getUsers();
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO user) {
		// logic to add user 
		
		UserDTO users = adminService.addUser(user);
		return new ResponseEntity<UserDTO>(users, HttpStatus.OK);
	}

}
