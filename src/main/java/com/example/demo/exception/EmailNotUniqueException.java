package com.example.demo.exception;

public class EmailNotUniqueException extends RuntimeException{

	public EmailNotUniqueException(String message) {
        super(message);
    }
}