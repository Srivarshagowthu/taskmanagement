package com.varsha.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.package.base.*")
public class ProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessApplication.class, args);
	}

}
