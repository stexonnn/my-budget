package com.example.demo.dto;

public class TransactionDTO {

	  private Long account;
	  private String description;
	  private Double amount;
	  private String type;
	  
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


		  
		    
}
