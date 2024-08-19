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
import com.example.demo.security.CurrentUser;

@Service
public class AccountService {
	
	@Autowired
	CurrentUser currentUser;
	
	@Autowired
	private CurrencyService currencyService;
	
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
	
	public List<AccountDTO> getAccounts() throws Exception {
		User user = currentUser.getUser();
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
	
	/*
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
*/
	
	private List<AccountDTO> convertToDTO(List<Account> accounts) throws Exception {
        return accounts.stream()
                       .map(account -> new AccountDTO(account.getId(),account.getName(),account.getCurrency(),account.getBalance()))
                       .collect(Collectors.toList());
    }


	
	public Optional<User> getUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByEmail(username);
        return userOptional;
	}

	
	public List<AccountDTO> updateWithDefaultValues(List<AccountDTO> accounts) throws Exception {
		double defaultCurrencyValue = currencyService.defaultValue(accounts.get(0).getCurrency());
	    System.out.println("Default Currency Value: " + defaultCurrencyValue);
	    
	    for (AccountDTO acc : accounts) {
	        // Ensure balance and default currency values are treated as Double
	        Double balance = acc.getBalance();  // Ensure this returns a Double
	        Double currencyValue = currencyService.defaultValue(acc.getCurrency());  // Ensure this returns a Double

	        // Debugging: Check the types before multiplication
	        System.out.println("Balance Type: " + ((Object) balance).getClass().getName());
	        System.out.println("Currency Value Type: " + ((Object) currencyValue).getClass().getName());

	        // Perform the multiplication and set the balance in the default currency
	        Double balanceInDefaultCurrency = balance * currencyValue;
	        acc.setBalanceInDefaultCurrency(balanceInDefaultCurrency);

	        // Debugging: Check the result
	        System.out.println("Balance in Default Currency: " + balanceInDefaultCurrency);
	    }
	    
	    return accounts;
		
	}

}
