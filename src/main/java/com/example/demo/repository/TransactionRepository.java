package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction,Integer>{
	
	@Modifying
	@Query("DELETE FROM Transaction t WHERE t.account.id=:accountId")
	void deleteByAccountId(Long accountId);
	
	@Query("SELECT t FROM Transaction t JOIN t.account a WHERE a.user.id = :userId")
	List<Transaction> getTransactionsByUser(Long userId);
	
	@Query("SELECT t FROM Transaction t JOIN t.account a WHERE a.id = :accountId")
	List<Transaction> getTransactionsByAccount(Long accountId);

}
