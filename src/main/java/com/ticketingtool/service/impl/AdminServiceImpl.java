package com.ticketingtool.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketingtool.dto.RoleDTO;
import com.ticketingtool.dto.UserDTO;
import com.ticketingtool.entity.Role;
import com.ticketingtool.entity.User;
import com.ticketingtool.exception.ResourceNotFoundException;
import com.ticketingtool.repository.RoleRepo;
import com.ticketingtool.repository.UserRepo;
import com.ticketingtool.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<UserDTO> getUsers() {

		List<UserDTO> users = userRepo.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		return users;
	}

	@Override
	public UserDTO addUser(UserDTO userdto) {
		User user = modelMapper.map(userdto, User.class);
		Set<Role> roles = new HashSet();
		for (RoleDTO role : userdto.getRoles()) {
			Integer roleid = role.getRoleId();
			Role foundrole = roleRepo.findById(roleid).orElseThrow(()->
			new ResourceNotFoundException("Role with id "+roleid+" not found"));
			roles.add(foundrole);
		}
		user.setRoles(roles);
		User saveduser = userRepo.save(user);
		UserDTO dto = modelMapper.map(saveduser, UserDTO.class);
		return dto;
	}

}
