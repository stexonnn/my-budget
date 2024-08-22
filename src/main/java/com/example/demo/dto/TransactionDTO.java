package com.example.demo.dto;

public class TransactionDTO {

	  private Long account;
	  private String description;
	  private Double amount;
	  private Double amountInDefaultCurrrency;
	  private String type;
	  private String currency;
	  private String accountName;
	  
	  public Long getAccount() {
			return account;
		}
	  
	  public void setAccount(Long long1) {
			this.account = long1;
		}
	  
	  public String getDescription() {
			return description;
		}
	  
	  public void setDescription(String description) {
			this.description = description;
		}
	  
	  public Double getAmount() {
			return amount;
		}
	  
	  public void setAmount(Double amount) {
			this.amount = amount;
		}
	  public String getType() {
			return type;
		}
	  
	  public void setType(String type) {
			this.type = type;
		}

	public Double getAmountInDefaultCurrrency() {
		return amountInDefaultCurrrency;
	}

	public void setAmountInDefaultCurrrency(Double amountInDefaultCurrrency) {
		this.amountInDefaultCurrrency = amountInDefaultCurrrency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	


		  
		    
}
