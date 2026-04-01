package com.example.alazani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling       // so that we can run some methods periodically
@SpringBootApplication
public class AlazaniApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlazaniApplication.class, args);
	}
}
