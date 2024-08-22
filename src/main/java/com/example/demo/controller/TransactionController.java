package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.service.ITransactionService;
import com.example.demo.service.TransactionService;

@RestController
@RequestMapping("/api/transactions/")
public class TransactionController {

	@Autowired
	ITransactionService transactionService;
	
	@GetMapping("getAll")
	public ResponseEntity<List<TransactionDTO>> getTransactions() throws Exception {
		return ResponseEntity.ok(transactionService.getAllTransactions());
		
	}
	
	@GetMapping("get/{account_id}")
	public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable String account_id) throws Exception {
		return ResponseEntity.ok(transactionService.getTransactions(account_id));
		
	}
	
	@PostMapping("save")
	public ResponseEntity<?> save(@RequestBody TransactionDTO transactionDTO) {
		transactionService.save(transactionDTO);
		return ResponseEntity.ok("true");
	}
}
