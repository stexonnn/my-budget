package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,Long> {

	public Optional<TransactionType> findByName(String name);
}
