package com.example.demo.service;

import java.util.ArrayList;
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
import com.example.demo.security.CurrentUser;
import com.example.demo.security.ICurrentUser;

@Service
public class AccountService implements IAccountService {
	
	@Autowired
	ICurrentUser currentUser;
	
	@Autowired
	private ICurrencyService currencyService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public AccountDTO createAccount(AccountDTO accountDTO) throws Exception {
		Account account = new Account();
		
        User user = currentUser.getUser();
		account.setName(accountDTO.getName());
		account.setBalance(accountDTO.getBalance());
		account.setCurrency(accountDTO.getCurrency());
		account.setUser(user);
		Account acc =  accountRepository.save(account);
		List<AccountDTO> accountsDTO = this.convertToDTO((List<Account>) acc);
		if (accountsDTO.size()<1)
			return null;
		return accountsDTO.get(0);
	}
	
	public List<AccountDTO> getAccounts() throws Exception {
		User user = currentUser.getUser();
		if (user!=null) {
			List<Account> accounts= accountRepository.findByUserId(user.getId());
				return convertToDTO(accounts);
		}
		 else
			    return null;
	}
	
	
	
	 public AccountDTO getAccount(String accountName) throws Exception {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = currentUser.getUser();

        	
            Optional<Account> accountOptional = accountRepository.findByUserIdAndName(user.getId(), accountName);

            if (accountOptional.isPresent()) {
               Account account = accountOptional.get();
               return new AccountDTO(account.getName(),account.getCurrency(),account.getBalance());
            } else {
                throw new NoSuchElementException("No account found with name: " + accountName);
            }
                     
    }
	
	
	
	private List<AccountDTO> convertToDTO(List<Account> accounts) throws Exception {
        return accounts.stream()
                       .map(account -> new AccountDTO(account.getId(),account.getName(),account.getCurrency(),account.getBalance()))
                       .collect(Collectors.toList());
    }


	
	
	public List<AccountDTO> updateWithDefaultValues(List<AccountDTO> accounts) throws Exception {
	    for (AccountDTO acc : accounts) {
	        // Ensure balance and default currency values are treated as Double
	        
	        Double balanceInDefaultCurrency = currencyService.convertToDefault(acc.getBalance(), acc.getCurrency());
	        System.out.println(acc.getBalance() + " " + acc.getCurrency());
	        acc.setBalanceInDefaultCurrency(balanceInDefaultCurrency);
	    }
	    
	    return accounts;
		
	}

}
