package com.ticketingtool.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ticketingtool.controller.LoginController;
import com.ticketingtool.entity.User;
import com.ticketingtool.repository.UserRepo;

@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo repo;
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		User user = repo.findById(username).
				orElseThrow(() -> new UsernameNotFoundException("User not found with email id : "+username));
       logger.info("CustomUserDetailsServiceImpl executing ... "+user.toString());
       logger.info("CustomUserDetailsServiceImpl executing ... "+user.getRoles().toString());
		return user;

	}


}
