package com.ticketingtool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketingtool.dto.JwtRequest;
import com.ticketingtool.dto.JwtResponse;
import com.ticketingtool.security.CustomUserDetailsServiceImpl;
import com.ticketingtool.security.JwtUtility;

@RestController
@RequestMapping("/health_care_system/api/v1")
public class LoginController {
	@Autowired
	private CustomUserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthenticationManager authmanager;

	@Autowired
	private JwtUtility jwtutill;

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login/welcome")
	public String welcome() {
		logger.info("welcome api called");
		return "welcome";
	}

    @PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtrequest)
	{
    	logger.info("login api called");
    	String username = jwtrequest.getUsername();
    	String password = jwtrequest.getPassword();
    	
    	 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
         try {
        	 authmanager.authenticate(authentication);

         } 
         catch (BadCredentialsException e) {
             throw new BadCredentialsException(" Invalid Username or Password  !!");
         }
		

         UserDetails userDetails = userDetailsService.loadUserByUsername(username);
         String token = this.jwtutill.GenerateToken(userDetails.getUsername());
          System.out.println("token  "+token);
         JwtResponse response = new JwtResponse(token);
         return new ResponseEntity<>( response, HttpStatus.OK);
	}
}
