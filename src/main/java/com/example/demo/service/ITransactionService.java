package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Account;
import com.example.demo.model.Transaction;

@Service
public interface ITransactionService {

	public boolean save(TransactionDTO transactionDTO);
		
	public List<TransactionDTO> getAllTransactions() throws Exception;
	
	public List<TransactionDTO> getTransactions(String accountId) throws Exception;
	
	public List<TransactionDTO> convertToDTO(List<Transaction> transactions);
	
	public Transaction convertFromDTO(TransactionDTO tDTO);
	
	public List<TransactionDTO> updateWithDefaultValues(List<TransactionDTO> transactions);
			
		    
		    
			
		
}
