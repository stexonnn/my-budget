package com.example.demo.security;

import com.example.demo.model.User;

public interface ICurrentUser {
	
	
	public  User getUser() throws Exception;
}
