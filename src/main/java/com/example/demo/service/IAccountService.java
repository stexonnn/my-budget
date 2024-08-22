package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.Account;

@Service
public interface IAccountService {
	
	
	public AccountDTO createAccount(AccountDTO accountDTO) throws Exception;
	public List<AccountDTO> getAccounts() throws Exception;   
	
	
	
	public AccountDTO getAccount(String accountName) throws Exception;
	public List<AccountDTO> updateWithDefaultValues(List<AccountDTO> accountsDTO) throws Exception;
        

}
