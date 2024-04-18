package com.ticketingtool;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HealthCareTicketingToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareTicketingToolApplication.class, args);
		System.out.println("health care system up and running");
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
