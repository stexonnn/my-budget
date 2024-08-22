package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.LoadDataService;
import com.example.demo.service.UserService;

@SpringBootApplication
@EntityScan(basePackages = "com.example.demo.model")
public class MyBudgetApplication implements CommandLineRunner {
	
	@Autowired
	 private LoadDataService loadDataService;
	


	 public static void main(String[] args) {
		SpringApplication.run(MyBudgetApplication.class, args);
	}


	  @Override
	  public void run(String... args) throws Exception {
	      this.loadDataService.loadData();
	    }

}
