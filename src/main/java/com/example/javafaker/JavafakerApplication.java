package com.example.javafaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JavafakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavafakerApplication.class, args);
	}

}
