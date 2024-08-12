package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthenticationRequestDTO;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
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
		Optional<Role>  optionalRole = roleRepository.findRoleByName("user");
		Role role = optionalRole.get();
		user.setRole(role);
		User createdUser= userRepository.save(user);
		String jwtToken = jwtService.generateToken(createdUser);
		AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO();
		authResponse.setToken(jwtToken);
		return authResponse;
	

	}

	public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authRequest) {
		 authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
				authRequest.getPassword()));
		return null;
	}

}
