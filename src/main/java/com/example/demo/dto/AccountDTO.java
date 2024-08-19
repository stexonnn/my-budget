package com.example.demo.dto;


public class AccountDTO {
	
	private Long id;
	private String name;
	private String currency;
	private double balance;
	
	private double balanceInDefaultCurrency;
	
	public AccountDTO()  {}
	
	public AccountDTO(Long id,String name, String currency, double balance) {
		this.id=id;
		this.name=name;
		this.currency=currency;
		this.balance=balance;
	}
	
	
	
	public AccountDTO(String name, String currency, Double balance) {
		this.name=name;
		this.currency=currency;
		this.balance=balance;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}



	public double getBalanceInDefaultCurrency() {
		return balanceInDefaultCurrency;
	}



	public void setBalanceInDefaultCurrency(double balanceInDefaultCurrency) {
		this.balanceInDefaultCurrency = balanceInDefaultCurrency;
	}

	
}
