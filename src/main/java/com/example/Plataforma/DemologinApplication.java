package com.example.Plataforma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemologinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemologinApplication.class, args);
	}

}
