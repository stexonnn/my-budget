package com.example.demo.xmlUtis;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Accounts")  
public class AccountsXml {

	
	private List<AccountXml> accounts;

	@XmlElement(name="Account")
	public List<AccountXml> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountXml> accounts) {
		this.accounts = accounts;
	}
	
	
	
}