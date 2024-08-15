package com.example.demo.dto;

public class AccountDTO {
	
	private String name;
	private String currency;
	private double balance;
	
	public AccountDTO(String name, String currency, double balance) {
		this.name=name;
		this.currency=currency;
		this.balance=balance;
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

	
}
