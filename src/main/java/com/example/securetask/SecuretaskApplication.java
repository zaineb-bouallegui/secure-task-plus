package com.example.securetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class SecuretaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuretaskApplication.class, args);
	}

}
