package com.example.indiapaymenthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

public class IndiapaymenthubApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndiapaymenthubApplication.class, args);
	}

}
