package com.example.demo.dto;

import lombok.Builder;

@Builder
public class AuthenticationResponseDTO {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
