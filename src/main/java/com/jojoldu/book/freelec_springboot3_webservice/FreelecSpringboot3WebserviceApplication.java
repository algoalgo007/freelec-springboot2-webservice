package com.jojoldu.book.freelec_springboot3_webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FreelecSpringboot3WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelecSpringboot3WebserviceApplication.class, args);
	}
}
