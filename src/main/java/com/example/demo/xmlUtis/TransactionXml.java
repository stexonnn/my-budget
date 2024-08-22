package com.example.demo.xmlUtis;

import javax.xml.bind.annotation.XmlElement;


public class TransactionXml {

	private String description;
	
	private  AmountXml amount;
	
	@XmlElement(name = "Description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement(name = "Amount")
	public AmountXml getAmount() {
		return amount;
	}
	
	public void setAmount(AmountXml amount) {
		this.amount = amount;
	}



}
