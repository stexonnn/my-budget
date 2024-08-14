package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction,Integer>{

	void deleteByAccount(Account account);

}
