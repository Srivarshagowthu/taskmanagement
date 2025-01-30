package com.ninjacart.task_mgmt_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskMgmtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskMgmtServiceApplication.class, args);
	}

}