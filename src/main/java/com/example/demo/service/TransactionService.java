package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.security.CurrentUser;
import com.example.demo.security.ICurrentUser;

@Service
public class TransactionService implements ITransactionService{
	
	@Autowired 
	ICurrentUser currentUser;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired 
	AccountRepository accountRepository;
	
	
	@Autowired
	ICurrencyService currencyService;

	public boolean save(TransactionDTO transactionDTO) {
		System.out.print("izvrsava se cuvanje");
		Transaction transaction = convertFromDTO(transactionDTO);
		transactionRepository.save(transaction);
		Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getAccount());
		Account account =accountOptional.get();
		account.setBalance(account.getBalance()+transactionDTO.getAmount());
		accountRepository.save(account);
		return true; // jos posla
		
		
	}
	public List<TransactionDTO> getAllTransactions() throws Exception {
		User user = currentUser.getUser();
		if (user==null)
			return null;
		List<Transaction> transactions = transactionRepository.getTransactionsByUser(user.getId());
		return convertToDTO(transactions);
		
		
	}
	
	public List<TransactionDTO> getTransactions(String accountId) throws Exception {
		Long accId = Long.parseLong(accountId);
		
		User user = currentUser.getUser();
		if (user==null)
			return null;
		List<Transaction> transactions = transactionRepository.getTransactionsByAccount(accId);
		return convertToDTO(transactions);
		
		
	}
	
	public List<TransactionDTO> convertToDTO(List<Transaction> transactions) {
		List<TransactionDTO> transactionsDTO = new ArrayList<>();
		for (Transaction t: transactions) {
			TransactionDTO tDTO = new TransactionDTO();
			if (t.getAccount()!=null)
				tDTO.setAccount(t.getAccount().getId());
			if (t.getAccount()!=null) {
				tDTO.setCurrency(t.getAccount().getCurrency());
				tDTO.setAccountName(t.getAccount().getName());
			}
			tDTO.setDescription(t.getDescription());
			tDTO.setAmount(t.getAmount());
			tDTO.setType(t.getType());
			
			transactionsDTO.add(tDTO);
		}
		this.updateWithDefaultValues(transactionsDTO);
		return transactionsDTO;
 	}
	
	public Transaction convertFromDTO(TransactionDTO tDTO) {
		if (tDTO == null)
			return null;
		Transaction transaction = new Transaction();
		Optional<Account> accountOptional = Optional.ofNullable(accountRepository.findById(tDTO.getAccount()).get());
		Account account = accountOptional.get();
		transaction.setAccount(account);
		transaction.setType(tDTO.getType());
		transaction.setDescription(tDTO.getDescription());
		transaction.setAmount(tDTO.getAmount());
		return transaction;
	}
	
	public List<TransactionDTO> updateWithDefaultValues(List<TransactionDTO> transactions) {
			
		    
		    for (TransactionDTO tran : transactions) {
		        // Ensure balance and default currency values are treated as Double
		        Double balance = tran.getAmount(); 
		        Optional<Account> accOptional = accountRepository.findById(tran.getAccount());
		        Account acc = accOptional.get();
                //null?	
		        Double balanceInDefaultCurrency = currencyService.convertToDefault(tran.getAmount(), acc.getCurrency());
		        tran.setAmountInDefaultCurrrency(balanceInDefaultCurrency);
		    }
		    
		    return transactions;
			
		}
		

}
