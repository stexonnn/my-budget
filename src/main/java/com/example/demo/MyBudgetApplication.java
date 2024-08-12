package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.demo.model")
public class MyBudgetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBudgetApplication.class, args);
	}

}
