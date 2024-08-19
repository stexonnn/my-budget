package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CurrencyService;

@RestController
@RequestMapping("/api/currency/")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("getAll")
    public ResponseEntity<List<String>> getCurrencies() {
        List<String> currencyList = new ArrayList<>(currencyService.getAll().keySet());
        currencyList.sort(String::compareTo);
        return ResponseEntity.ok(currencyList);
    }
    
    @GetMapping("getLastUpdated")
    public ResponseEntity<LocalDate> getLastUpdated() {
    	System.out.println("radii");
    	System.out.println(currencyService.getLastUpdated());
    	return ResponseEntity.ok(currencyService.getLastUpdated());
    }
    
    @PutMapping("updateDefaultCurrency")
    public ResponseEntity<?> updateDefaultCurrency(@RequestBody String value) {
    	System.out.print(value);
    	return ResponseEntity.ok(currencyService.setDefaultCurrency(value));
    }
}
