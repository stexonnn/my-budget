package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TransactionTypeDTO;
import com.example.demo.service.TransactionTypeService;

@RestController
@RequestMapping("/api/transactionType/")
public class TransactionTypeController {

	@Autowired
	TransactionTypeService tts;
	
	@GetMapping("getAll")
	public List<TransactionTypeDTO> getAll() {
		return tts.getAll();
	}
	
}
