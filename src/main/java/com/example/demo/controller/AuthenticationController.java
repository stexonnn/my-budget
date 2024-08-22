package com.example.demo.controller;

import java.util.List;


import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;

import com.example.demo.service.IAuthenticationService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authService;
	
    
    
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO authRequestDTO) throws NotFoundException {
    	AuthenticationResponseDTO authResponse = authService.authenticate(authRequestDTO);
        return  ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> createUser(@Valid @RequestBody RegisterRequestDTO registerDTO) throws Exception {
            AuthenticationResponseDTO authResponse = authService.registerUser(registerDTO);
            return ResponseEntity.ok(authResponse); 
    }
    
  
}
