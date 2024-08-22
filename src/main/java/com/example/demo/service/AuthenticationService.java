package com.example.demo.service;

import java.util.List;
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
import com.example.demo.exception.EmailNotUniqueException;
import com.example.demo.exception.InvalidRegisterException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;

@Service
public class AuthenticationService implements IAuthenticationService {
	
	@Autowired
	private ILoadDataService loadDataService;
	
	@Autowired
	private AccountRepository accountRepository;
	
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

	public AuthenticationResponseDTO registerUser(RegisterRequestDTO registerDTO) throws EmailNotUniqueException, InvalidRegisterException{
		Optional<User> existingUserOptional= userRepository.findByEmail(registerDTO.getEmail());
		 
		if (existingUserOptional.isPresent()) {
		    throw new EmailNotUniqueException("User with this email already exists");
		}
				
		User user = new User();
		user.setEmail(registerDTO.getEmail());
		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		Optional<Role>  optionalRole = roleRepository.findByName("user");
		Role role = optionalRole.orElseThrow(() -> new InvalidRegisterException("Role not found"));

		user.setRole(role);
		User createdUser= userRepository.save(user);
		if (createdUser == null) {
	        throw new InvalidRegisterException("Failed to save user");
	    }

		String jwtToken = jwtService.generateToken(createdUser);
		AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO();
		authResponse.setToken(jwtToken);
		addAccounts(user);
		return authResponse;
	

	}
	
	private void addAccounts(User user) {
		List<Account> accounts = loadDataService.getAccounts();
		for (Account a: accounts) {
			a.setUser(user);
			accountRepository.save(a);
		}
	}

	public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authRequest) throws NotFoundException {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				authRequest.getUsername(), authRequest.getPassword());
	    try {
		Authentication auth = authManager.authenticate(authToken);
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(auth);
	    } catch (AuthenticationException e) {
	        throw e;
	    }
		String email = authRequest.getUsername();
		Optional<User> userOptional = userRepository.findByEmail(email);
		User user = userOptional.orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
		String jwt= jwtService.generateToken(user);

		AuthenticationResponseDTO authResponse = new AuthenticationResponseDTO();
		authResponse.setToken(jwt);
		return authResponse;
	    }
		
	
	
	
	public boolean verifyPassword(String password, String hashedPassword) {
		return passwordEncoder.matches(password, hashedPassword);
	}
	


}
