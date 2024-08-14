package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;

public interface AccountRepository extends JpaRepository<Account,Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.account = :account")
    List<Transaction> findAllTransactionsByAccount(@Param("account") Account account);
}
