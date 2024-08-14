package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountDTO;
import com.example.demo.exception.InvalidAccountException;
import com.example.demo.exception.InvalidAuthException;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

@RestController
@RequestMapping("/api/account/")
public class AccountControler {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("createAccount")
	public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO ) throws  InvalidAccountException {
		
	            Account account = accountService.createAccount(accountDTO);
	            if (account!=null)
	            	return ResponseEntity.ok().build();
	            else 
	            	throw new InvalidAccountException("Account creation failed!");
	            	              
	}

}
