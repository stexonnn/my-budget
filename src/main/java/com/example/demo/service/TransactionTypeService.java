package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.dto.TransactionTypeDTO;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionType;
import com.example.demo.repository.TransactionTypeRepository;

@Service
public class TransactionTypeService {

	@Autowired
	TransactionTypeRepository ttr;
	
	public List<TransactionTypeDTO> getAll() {
		return convertToDTO(ttr.findAll());
	}
	
	public List<TransactionTypeDTO> convertToDTO(List<TransactionType> transactionTypes) {
		List<TransactionTypeDTO> transactionsDTO = new ArrayList<>();
		for (TransactionType  t: transactionTypes) {
			TransactionTypeDTO tDTO = new TransactionTypeDTO();
			tDTO.setName(t.getName());
			transactionsDTO.add(tDTO);
		}
		return transactionsDTO;
 	}
}
