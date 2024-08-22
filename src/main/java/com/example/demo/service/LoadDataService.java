package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.xmlUtis.AccountXml;
import com.example.demo.xmlUtis.AccountsXml;
import com.example.demo.xmlUtis.TransactionXml;

@Service
public class LoadDataService implements ILoadDataService{
	
		private List<Account> accounts= new ArrayList<Account>();
	

	
	    public void loadData() {
	        parseXmlFile( "my_budget_data.xml");
	        
	    }
	
	    public void parseXmlFile(String filePath) {
	    	File file = new File(filePath);
	    	
	        try {
				JAXBContext jaxbContext = JAXBContext.newInstance(AccountsXml.class);
		        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
		        AccountsXml accounts = (AccountsXml) jaxbUnmarshaller.unmarshal(file);
		        List<AccountXml> accountList=accounts.getAccounts();
		        for (AccountXml a: accountList) {
		        	
		        	Account acc = convertToAccount(a);
			        this.accounts.add(acc);
		        }	       
		        } catch (JAXBException e) {
				e.printStackTrace();
				
			}  
	    }
	    
	    private Account convertToAccount(AccountXml accXml) {
	    	System.out.println(accXml.getName());
	    		Account acc = new Account();
	    		acc.setBalance(accXml.getBalance());
	    		acc.setCurrency(accXml.getCurrency());
	    		acc.setName(accXml.getName());
	    		if (accXml.getTransactions()!=null && accXml.getTransactions().size()!=0)
	    			convertToTransactions(acc,accXml.getTransactions());
	    	
	    	return acc;
	    }
		    
		    private void convertToTransactions(Account acc,List<TransactionXml> transactionsXml) {
		    	if (transactionsXml==null)
		    		return;
		    	List<Transaction> transactions = new ArrayList<>();
		    	for (TransactionXml trXml: transactionsXml) {
		    		Transaction t = new Transaction();
		    		if (trXml.getAmount()!=null) {
		    			t.setAmount(trXml.getAmount().getValueAsDouble());
		    		t.setDescription(trXml.getDescription());
		    	}
		    		t.setAccount(acc);
		    		transactions.add(t);
		    		System.out.print(t.getAmount());
		       }
		    	acc.setTransactionList(transactions);
		        }
		
			public List<Account> getAccounts() {
				return accounts;
			   }
		
			public void setAccounts(List<Account> accounts) {
				this.accounts = accounts;
			    }
	}