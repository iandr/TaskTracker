package com.geekbrains.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.geekbrains.server.repositories"})
@EntityScan(basePackages = {"com.geekbrains.server.entities"})
public class SimpleRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SimpleRestApplication.class, args);
	}
}