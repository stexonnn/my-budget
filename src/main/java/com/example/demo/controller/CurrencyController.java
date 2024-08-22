package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CurrencyService;
import com.example.demo.service.ICurrencyService;

@RestController
@RequestMapping("/api/currency/")
public class CurrencyController {

    @Autowired
    private ICurrencyService currencyService;

    @GetMapping("getAll")
    public ResponseEntity<List<String>> getCurrencies() {
        List<String> currencyList = new ArrayList<>(currencyService.getAll().keySet());
        currencyList.sort(String::compareTo);
        return ResponseEntity.ok(currencyList);
    }
    
    @GetMapping("getLastUpdated")
    public ResponseEntity<Map<String,String>> getLastUpdated() {
    	System.out.println("radii");
    	System.out.println(currencyService.getLastUpdated());
    	String date = currencyService.getLastUpdated();
    	Map<String,String> responseMap = new HashMap<>();
    	responseMap.put("lastUpdated", date);
    	return ResponseEntity.ok(responseMap);
    }
    
    @PutMapping("updateDefaultCurrency")
    public ResponseEntity<?> updateDefaultCurrency(@RequestBody String value) {
    	System.out.print(value);
    	return ResponseEntity.ok(currencyService.setDefaultCurrency(value));
    }
    
    @GetMapping("getDefaultCurrency")
    public ResponseEntity<Map<String,String>> geteDefaultCurrency() {
    	String curr = currencyService.getDefaultCurrency();
    	Map<String,String> responseMap= new HashMap<>();
    	responseMap.put("currency", curr);
    	System.out.println("Vrednost" + currencyService.getDefaultCurrency());
    	return ResponseEntity.ok(responseMap);
    }
}
