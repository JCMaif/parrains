package com.simplon.parrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ParrainsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParrainsApplication.class, args);
	}

}
