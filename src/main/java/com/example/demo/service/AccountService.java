package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.UserRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Account createAccount(AccountDTO accountDTO) {
		Account account = new Account();

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> userOptional = userRepository.findByEmail(username);
		User user = userOptional.get();
		account.setName(accountDTO.getName());
		account.setBalance(accountDTO.getBalance());
		account.setCurrency(accountDTO.getCurrency());
		account.setUser(user);
		return accountRepository.save(account);
	}

}
