package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Account;

@Service
public interface ILoadDataService {

    public void parseXmlFile(String filePath);
	    
	public List<Account> getAccounts();

	public void setAccounts(List<Account> accounts);
}
