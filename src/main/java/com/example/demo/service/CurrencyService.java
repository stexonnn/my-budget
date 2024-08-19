package com.example.demo.service;

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
public class CurrencyService {

	private DefaultCurrency defaultCurrency;
	private RestTemplate restTemplate;
	
    private LocalDate lastUpdated;

    private Map<String, String> currentValues = new HashMap<>();
    private Map<String, Double> exchangeRates = new HashMap<>();

	private final String API_URL_LIST = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
    private static final String EXCHANGE_RATE_API = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/{base}.json";
	
	  @Autowired
	    public CurrencyService(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }

	    @PostConstruct
	    public void init() {
	    	this.defaultCurrency=DefaultCurrency.EUR;
	    	updateExchangeRates(this.defaultCurrency);
	        this.currentValues = this.getCurrencies();
	        this.exchangeRates = getExchangeRates();
	        this.lastUpdated = this.getLastUpdated();
	    }


	
	@SuppressWarnings("unchecked")
	public Map<String, String> getAll() {
		currentValues= restTemplate.getForObject(API_URL_LIST,HashMap.class);
		return currentValues;
	}
	
	  public void updateExchangeRates(DefaultCurrency defaultCurrency) {
		    String url = EXCHANGE_RATE_API.replace("{base}", defaultCurrency.toString().toLowerCase());
	        Map<String, Object> response = restTemplate.getForObject(url, HashMap.class);
	        if (response != null && response.containsKey(defaultCurrency.toString().toLowerCase())) {
	        	System.out.println(response);
	            exchangeRates = (Map<String, Double>) response.get(defaultCurrency.toString().toLowerCase());
	            
	            String dateString = (String) response.get("date");
	            if (dateString != null) {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                lastUpdated = LocalDate.parse(dateString, formatter);
	            }
	        }
	    }
	  
	  public boolean setDefaultCurrency(String currency) {
		  System.out.print("menjaa");
		  if ("EUR".equalsIgnoreCase(currency))
			  this.defaultCurrency=DefaultCurrency.EUR;
		  else if ("BTC".equalsIgnoreCase(currency))
			  this.defaultCurrency=DefaultCurrency.BTC;
		  else {
			  throw new IllegalArgumentException();
			  }
		  updateExchangeRates(this.defaultCurrency);
		  return true;
		  }
	  
	  public DefaultCurrency getDefaultCurrency() {
		  return this.defaultCurrency;
	  }
	  
	    public Map<String, String> getCurrencies() {
	        return currentValues;
	    }

	    public Map<String, Double> getExchangeRates() {
	        return exchangeRates;
	    }

	    public LocalDate getLastUpdated() {
	        return this.lastUpdated;
	    }

	   /* @Scheduled(fixedRate = 3600000*24) // every 24  hours
	    public void scheduledExchangeRateUpdate() {
	        updateExchangeRates(DefaultCurrency.EUR); 
	        updateExchangeRates(DefaultCurrency.BTC); 
	    }*/
	
	    public double defaultValue(String from) {
	    	if ("eur".equalsIgnoreCase(from))
	    		return 1.0;
	    	
	    	return this.exchangeRates.get(from);
	    }
	
}
