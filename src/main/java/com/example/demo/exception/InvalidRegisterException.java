package com.example.demo.exception;

public class InvalidRegisterException extends RuntimeException {

	public InvalidRegisterException(String message) {
        super(message);
    }
}