package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 @RequestMapping("getTotalValue") 
	 public ResponseEntity<?> getTotalValue() {
		 return ResponseEntity.ok(userService.getTotalValue());
	 }

}
