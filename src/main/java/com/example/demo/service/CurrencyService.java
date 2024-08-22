package com.example.demo.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.enums.DefaultCurrency;

import jakarta.annotation.PostConstruct;

@Service
public class CurrencyService implements ICurrencyService {

	private String defaultCurrency;
	private RestTemplate restTemplate;
	
    private LocalDate lastUpdated;

    private Map<String, String> currentValues = new HashMap<>();
    private Map<String, Double> exchangeRates = new HashMap<>();

	private final String API_URL_LIST = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
    private static final String EXCHANGE_RATE_API = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/eur.json";
	
	  @Autowired
	    public CurrencyService(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }

	    @PostConstruct
	    public void init() {
	    	updateExchangeRates();
	        this.currentValues = this.getCurrencies();
	        this.exchangeRates = getExchangeRates();
	    }


	
	@SuppressWarnings("unchecked")
	public Map<String, String> getAll() {
		currentValues= restTemplate.getForObject(API_URL_LIST,HashMap.class);
		return currentValues;
	}
	
	  public void updateExchangeRates() {
		  if (this.defaultCurrency==null)
			  this.defaultCurrency="eur";
	        Map<String, Object> response = restTemplate.getForObject(EXCHANGE_RATE_API, HashMap.class);
	        if (response != null && response.containsKey(defaultCurrency.toLowerCase())) {
	            exchangeRates = (Map<String, Double>) response.get(defaultCurrency.toString().toLowerCase());
	            String dateString = (String) response.get("date");
	            if (dateString != null) {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                lastUpdated = LocalDate.parse(dateString, formatter);
	            }
	        }
	    }
	  
	  public boolean setDefaultCurrency(String currency) {
		 this.defaultCurrency=currency;
		 return true;
		  }
	  
	  public String getDefaultCurrency() {
		 return this.defaultCurrency;
	  }
	  
	    public Map<String, String> getCurrencies() {
	        return currentValues;
	    }

	    public Map<String, Double> getExchangeRates() {
	        return exchangeRates;
	    }

	    public String getLastUpdated() {
	    	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	         String formattedDate = this.lastUpdated.format(formatter);
	        return formattedDate;
	    }

	    @Scheduled(fixedRate = 3600000*24) // every 24  hours
	    public void scheduledExchangeRateUpdate() {
	        updateExchangeRates(); 
	    }
	
	    public double euroRate(String from) {
	    	if ("eur".equalsIgnoreCase(from))
	    		return 1.0;
	    	
	    	return this.exchangeRates.get(from.toLowerCase());
	    }
	    
	    public double convertToDefault(double value,String from) {
	    	if (from!=null && from.equalsIgnoreCase(this.defaultCurrency)) {
	    		return value;
	    	}
	    	double euroRate = euroRate(from);
	    	double valueInEuro = value  / euroRate;
	    	
	    	double defaultRate = 0.0;
	    	if ("eur".equalsIgnoreCase(this.defaultCurrency))
	    		 defaultRate=1.0;
	    	else 
	    		 defaultRate = this.exchangeRates.get(this.defaultCurrency.toLowerCase());
	    	
	    	return valueInEuro*defaultRate;
	    }
	    
	    
	    	
	    	
	    }
	    		
	    

		

