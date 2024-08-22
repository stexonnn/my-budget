package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.IUserService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	 @GetMapping("getTotalValue") 
	 public ResponseEntity<?> getTotalValue() throws Exception {
		 return ResponseEntity.ok(userService.getTotalValue());
	 }
	 
	 @DeleteMapping("delete")
	 public ResponseEntity<Void> deleteAllData() {
		 System.out.println("dletuje se");
		   boolean successful = userService.deleteAllAccounts();
	        if (!successful) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       }
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
		 
	 }


