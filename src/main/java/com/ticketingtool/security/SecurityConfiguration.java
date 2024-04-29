package com.ticketingtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.transaction.Transactional;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {

	@Autowired
	CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
	@Autowired
	JwtFilter jwtFilter;

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailsServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	private void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(customizer -> {

			customizer.requestMatchers(HttpMethod.GET, "/health_care_system/api/v1/patient/ticket-categories")
					.hasAnyAuthority("ROLE_PATIENT");
			customizer.requestMatchers(HttpMethod.POST, "/health_care_system/api/v1/patient/{emailId}")
					.hasAnyAuthority("ROLE_PATIENT");
			customizer.requestMatchers(HttpMethod.GET, "/health_care_system/api/v1/patient/tickets/users/{emailId}")
			.hasAnyAuthority("ROLE_PATIENT");
			customizer.requestMatchers(HttpMethod.GET, "/health_care_system/api/v1/patient/{emailId}/ticket/{ticketId}")
			.hasAnyAuthority("ROLE_PATIENT");
			customizer.requestMatchers(HttpMethod.DELETE, "/health_care_system/api/v1/patient/ticket/{id}")
			.hasAnyAuthority("ROLE_PATIENT");
			customizer.requestMatchers(HttpMethod.GET, "/health_care_system/api/v1/patient/welcome").permitAll();

			customizer.requestMatchers(HttpMethod.POST, "/health_care_system/api/v1/login").permitAll();
			customizer.requestMatchers(HttpMethod.GET, "/health_care_system/api/v1/login/welcome").permitAll();
			customizer.requestMatchers(HttpMethod.POST, "/health_care_system/api/v1/patient/").permitAll();
		}).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();

	}
}
