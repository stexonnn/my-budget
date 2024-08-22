package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public interface ICurrencyService {
	
		 public Map<String, String> getAll();
		
		
		 public void updateExchangeRates();
		  
		 public boolean setDefaultCurrency(String currency);
			  
		  
		 public String getDefaultCurrency();
		  
		 public Map<String, String> getCurrencies();
		        
	    

	     public Map<String, Double> getExchangeRates(); 
	    
	     public String getLastUpdated();
	    	

	     public double convertToDefault(double value,String from);
	    	
	    
	    

}
