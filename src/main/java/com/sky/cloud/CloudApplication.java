package com.sky.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.sky.cloud.repository")
public class CloudApplication {

	public static void main(String[] args) {

		SpringApplication.run(CloudApplication.class, args);
	}

}
