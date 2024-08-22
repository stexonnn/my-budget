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
import com.example.demo.security.CurrentUser;
import com.example.demo.security.ICurrentUser;

import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {
	
	@Autowired
	ICurrentUser currentUser;
	
	@Autowired
	ICurrencyService currencyService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired 
	private TransactionRepository transactionRepository;
	
	@Transactional
	public boolean deleteAllAccounts() {
		try {
			User user = currentUser.getUser();
		
		List<Account> accounts = accountRepository.findByUserId(user.getId());

		for (Account account: accounts) {
			transactionRepository.deleteByAccountId(account.getId());
	        accountRepository.delete(account);
		}
		
		return true;
		}  catch (Exception e) {
	       
	        return false;
	    }
	}
	
	public double getTotalValue() throws Exception {
		User user = currentUser.getUser();
		double totalValue=0.0;
    	List<Account> userAccounts= accountRepository.findByUserId(user.getId());
    	for (Account a: userAccounts) {
    		double balance = a.getBalance();
    		String accountCurrency = a.getCurrency();
    		if (a.getCurrency()!=null) {
    			 balance = balance + currencyService.convertToDefault(a.getBalance(), accountCurrency);
    		}
    		totalValue+=balance;
    	}
    	return totalValue;
	}
}
