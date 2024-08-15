package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.exception.UserNotFoundException;
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

		Optional<User> userOptional = getUser();
		User user = userOptional.get();
		account.setName(accountDTO.getName());
		account.setBalance(accountDTO.getBalance());
		account.setCurrency(accountDTO.getCurrency());
		account.setUser(user);
		return accountRepository.save(account);
	}
	
	public List<AccountDTO> getAccounts() {
		Optional<User> userOptional = getUser();
		User user = userOptional.get();
		if (user!=null) {
			List<Account> accounts= accountRepository.findByUserId(user.getId());
				return convertToDTO(accounts);
		}
		 else
			    return null;
	}
	
	public AccountDTO getAccount(String accountName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = getUser();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Account> accountOptional = accountRepository.findByUserIdAndName(user.getId(), accountName);

            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                return new AccountDTO(account.getName(),account.getCurrency(),account.getBalance());
            } else {
                throw new NoSuchElementException("No account found with name: " + accountName);
            }
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
	
	
	public double getTotalValue() {
		Optional<User> userOptional = getUser();

        if (userOptional.isPresent()) {
        	double totalValue=0.0;
        	User user = userOptional.get();
        	List<Account> userAccounts= accountRepository.findByUserId(user.getId());
        	for (Account a: userAccounts) {
        		totalValue+=a.getBalance();
        	}
        	return totalValue;
        
        	
        }

		throw new UserNotFoundException("User not found");
	}

	
	private List<AccountDTO> convertToDTO(List<Account> accounts) {
        return accounts.stream()
                       .map(account -> new AccountDTO(account.getName(),account.getCurrency(),account.getBalance()))
                       .collect(Collectors.toList());
    }


	
	public Optional<User> getUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional;
	}


}
