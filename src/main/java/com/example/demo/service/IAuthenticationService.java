package com.example.demo.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.exception.EmailNotUniqueException;
import com.example.demo.exception.InvalidRegisterException;

@Service
public interface IAuthenticationService {
	
	public AuthenticationResponseDTO registerUser(RegisterRequestDTO registerDTO) throws EmailNotUniqueException, InvalidRegisterException;
	
	public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authRequest) throws NotFoundException;
		
	
	
	

}
