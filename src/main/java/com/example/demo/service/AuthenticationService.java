package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private RoleRepository roleRepository;

	public AuthenticationResponseDTO registerUser(RegisterRequestDTO registerDTO) {
		User user = new User();
		user.setEmail(registerDTO.getEmail());
		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		Optional<Role>  optionalRole = roleRepository.findByName("user");
		Role role = optionalRole.get();
		user.setRole(role);
		User createdUser= userRepository.save(user);
		String jwtToken = jwtService.generateToken(createdUser);
		AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO();
		authResponse.setToken(jwtToken);
		return authResponse;
	

	}

	public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authRequest) throws NotFoundException {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword());
		System.out.println("PROSLO");
		System.out.println(authRequest.getUsername() + " username");
		System.out.println(authRequest.getPassword() + " password");
	    try {
		Authentication auth = authManager.authenticate(authToken);
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);
		System.out.println("authentication successfu ");
	    } catch (AuthenticationException e) {
	        System.out.println("Authentication failed: " + e.getMessage());
	        throw e;
	    }
		String email = authRequest.getUsername();
		System.out.println(email);
		Optional<User> userOptional = userRepository.findByEmail(email);
		System.out.println(userOptional.get() + " objekat");
		User user = userOptional.orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
		System.out.println("PROSLO 2");
		String jwt= jwtService.generateToken(user);
		System.out.println(jwt + " jwt token");

		AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO();
		authResponse.setToken(jwt);
		System.out.println(jwt + " jwt token 2");
		return authResponse;
	    }
		
	
	
	
	public boolean verifyPassword(String password, String hashedPassword) {
		return passwordEncoder.matches(password, hashedPassword);
	}
	


}
