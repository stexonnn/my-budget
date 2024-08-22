package com.example.demo.exception;

import java.util.List;

import org.springframework.validation.FieldError;

public class ErrorResponse {
	
    private String message;
    private int statusCode;
    private List<FieldError> fieldErrors;

    public ErrorResponse() {

    }

    public ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ErrorResponse(String message, int value, List<FieldError> fieldErrors) {
		this.message=message;
		this.statusCode=value;
		this.fieldErrors=fieldErrors;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}
