package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface IUserService {
	
	public boolean deleteAllAccounts();
		
	public double getTotalValue() throws Exception;
		
}
