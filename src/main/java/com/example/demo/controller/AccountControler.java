package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			System.out.print(accountDTO.getBalance());
	            Account account = accountService.createAccount(accountDTO);
	            if (account!=null)
	            	return ResponseEntity.ok().build();
	            else 
	            	throw new InvalidAccountException("Account creation failed!");
	            	              
	}
	
	@GetMapping("getAccounts")
	public ResponseEntity<List<AccountDTO>> getAccounts() throws Exception  {
	            List<AccountDTO> accountsDTO = accountService.getAccounts();
	            accountsDTO = accountService.updateWithDefaultValues(accountsDTO);
	            return ResponseEntity.ok(accountsDTO);
	            	              
	}
	
	@GetMapping("getAccount/{accountName}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountName )  {
		
	            AccountDTO accountDTO = accountService.getAccount(accountName);
	            return ResponseEntity.ok(accountDTO);
	            	              
	}
	/*
	@GetMapping("gettTotalValue")
	public ResponseEntity<Double> getTotalValue()  {
		
	            Double totalValue = accountService.getTotalValue();
	            return ResponseEntity.ok(totalValue);
	            	              
	}*/

}
