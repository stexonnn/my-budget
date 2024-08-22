package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String currency;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transactions;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Transaction> getTransactionsList() {
		List<Transaction> tran = new ArrayList<>(this.transactions);
		return tran;
	}
	
	public void setTransactionList(List<Transaction> tran) {
		this.transactions = new HashSet<>(tran);
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", currency=" + currency + ", balance=" + balance + ", user="
				+ user + ", transactions=" + transactions + "]";
	}

    
	
}
