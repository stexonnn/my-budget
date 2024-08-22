package com.example.demo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class CurrentUser implements ICurrentUser {

	@Autowired 
	UserRepository userRepository;
	
	
	public  User getUser() throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByEmail(username);
       
        if (userOptional.isEmpty()) {
            throw new RuntimeException("No user found");
        }
 
        return userOptional.get();
	}
}
