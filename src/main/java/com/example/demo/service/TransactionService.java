package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionType;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.TransactionTypeRepository;
import com.example.demo.security.CurrentUser;

@Service
public class TransactionService {
	
	@Autowired 
	CurrentUser currentUser;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired 
	AccountRepository accountRepository;
	
	@Autowired 
	TransactionTypeRepository ttRepository;

	public boolean save(TransactionDTO transactionDTO) {
		System.out.print("izvrsava se cuvanje");
		Transaction transaction = convertFromDTO(transactionDTO);
		transactionRepository.save(transaction);
		Optional<Account> accountOptional = accountRepository.findById(transactionDTO.getAccount());
		Account account =accountOptional.get();
		account.setBalance(account.getBalance()+transactionDTO.getAccount());
		accountRepository.save(account);
		return true; // jos posla
		
		
	}
	public List<TransactionDTO> getAllTransactions() {
		User user = currentUser.getUser();
		if (user==null)
			return null;
		List<Transaction> transactions = transactionRepository.getTransactionsByUser(user.getId());
		return convertToDTO(transactions);
		
		
	}
	
	public List<TransactionDTO> getTransactions(String accountId) {
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
			
			tDTO.setDescription(t.getDescription());
			tDTO.setAmount(t.getAmount());
			if (t.getTransactionType()!=null)
				tDTO.setType(t.getTransactionType().getName());
			
			transactionsDTO.add(tDTO);
		}
		return transactionsDTO;
 	}
	
	public Transaction convertFromDTO(TransactionDTO tDTO) {
		System.out.println(tDTO.getDescription() + tDTO.getType() + tDTO.getAccount() + tDTO.getAmount());
		if (tDTO == null)
			return null;
		Transaction transaction = new Transaction();
		Optional<Account> accountOptional = Optional.ofNullable(accountRepository.findById(tDTO.getAccount()).get());
		Account account = accountOptional.get();
		Optional<TransactionType> ttOptinoal = Optional.ofNullable(ttRepository.findByName(tDTO.getType()).get());
		TransactionType tt = ttOptinoal.get();
		transaction.setAccount(account);
		transaction.setTransactionType(tt);
		transaction.setDescription(tDTO.getDescription());
		transaction.setAmount(tDTO.getAmount());
		return transaction;
	}
	
}
