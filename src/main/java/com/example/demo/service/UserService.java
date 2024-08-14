package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private TransactionRepository transactionRepository;
	
	//  @Transactional to ensure that all database operations are performed within a transaction
	@Transactional
	public boolean deleteAllAccounts() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> userOptional = userRepository.findByEmail(username);
		  if (userOptional.isEmpty()) 
	            return false;
	        
		User user = userOptional.get();
		
		List<Account> accounts = user.getAccounts();
		
		for (Account account: accounts) {
			transactionRepository.deleteByAccount(account);
			accountRepository.delete(account);
		}
		
		return true;
	}
}
