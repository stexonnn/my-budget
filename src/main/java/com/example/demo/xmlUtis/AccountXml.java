package com.example.demo.xmlUtis;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Account")
public class AccountXml {

	private String name;
	private String currency;
	
	private double balance;

	
	private List<TransactionXml> transactions;

	
    @XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute(name = "currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@XmlElement(name = "Balance")
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	

    @XmlElementWrapper(name = "Transactions")
    @XmlElement(name = "Transaction")
	public List<TransactionXml>  getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<TransactionXml> transactions) {
		this.transactions =transactions;
	}
	
	
}