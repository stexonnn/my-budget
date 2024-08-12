package com.example.demo.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.exception.InvalidAuthException;
import com.example.demo.exception.InvalidRegisterException;
import com.example.demo.model.User;
import com.example.demo.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

  

    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> createUser( @RequestBody RegisterRequestDTO registerDTO) throws Exception {
        try {
            AuthenticationResponseDTO authResponse = authService.registerUser(registerDTO);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {

            throw new InvalidRegisterException("Registration failed");
        }
    }
    
    @PostMapping(value = "authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponseDTO> authUser(@RequestBody AuthenticationRequestDTO authRequest) throws Exception {
        try {
            return  ResponseEntity.ok(authService.authenticate(authRequest));
        }    catch (Exception e) {

            throw new InvalidAuthException("Authentication failed");
        }    
    }
}
