package com.carewave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarewaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarewaveApplication.class, args);
	}

}
